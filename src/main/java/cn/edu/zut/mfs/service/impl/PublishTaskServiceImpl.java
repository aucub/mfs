package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.PublishTaskService;
import cn.edu.zut.mfs.service.ReturnCallbackService;
import io.cloudevents.CloudEvent;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
public class PublishTaskServiceImpl implements PublishTaskService {
    private final RabbitTemplate rabbitTemplate;
    private AmqpAdmin amqpAdmin;
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
        rabbitTemplate.setReturnsCallback(returnCallbackService);
        rabbitTemplate.setUsePublisherConnection(true);
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Autowired
    public void setReturnCallbackService(ReturnCallbackService returnCallbackService) {
        this.returnCallbackService = returnCallbackService;
    }

    @Transactional
    public void publish(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        if (metadataHeader.getExchange() == null || "".equals(metadataHeader.getExchange())) {
            if (metadataHeader.getRoutingKey() != null) {
                if (amqpAdmin.getQueueInfo(metadataHeader.getRoutingKey()) == null)
                    throw new BaseException("队列不存在");
            } else throw new BaseException("未指定投递对象");
        }
        PublishServiceImpl.send(userId, metadataHeader, cloudEventFlux, influxDBService, rabbitTemplate);
    }
}
