package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.ConfirmCallbackService;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.PublishTaskService;
import cn.edu.zut.mfs.service.ReturnCallbackService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
public class PublishTaskServiceImpl implements PublishTaskService {
    private static AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private ConfirmCallbackService confirmCallbackService;
    private ReturnCallbackService returnCallbackService;

    @Autowired
    public PublishTaskServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.setConfirmCallback(confirmCallbackService);
        rabbitTemplate.setReturnsCallback(returnCallbackService);
        rabbitTemplate.setUsePublisherConnection(true);
    }

    @Autowired
    public static void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        PublishTaskServiceImpl.amqpAdmin = amqpAdmin;
    }

    @Autowired
    public void setConfirmCallbackService(ConfirmCallbackService confirmCallbackService) {
        this.confirmCallbackService = confirmCallbackService;
    }

    @Autowired
    public void setReturnCallbackService(ReturnCallbackService returnCallbackService) {
        this.returnCallbackService = returnCallbackService;
    }

    @Transactional
    public void publish(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        if (metadataHeader.getExchange() == null || metadataHeader.getExchange() == "") {
            if (metadataHeader.getRoutingKey() != null) {
                if (amqpAdmin.getQueueInfo(metadataHeader.getRoutingKey()) == null)
                    throw new BaseException("队列不存在");
            } else throw new BaseException("未指定投递对象");
        }
        cloudEventFlux.limitRate(100).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            Thread.startVirtualThread(() -> {
                PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource().toString(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Integer.valueOf((String) cloudEvent.getExtension("priority")), (String) cloudEvent.getExtension("expiration"), Integer.valueOf((String) cloudEvent.getExtension("delay")), (String) cloudEvent.getExtension("dataContentType"), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(cloudEvent.getData().toBytes()), cloudEvent.getTime().toInstant());
                influxDBService.publish(publishRecord);
            });

        });
    }
}
