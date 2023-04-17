package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.ConfirmCallbackService;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.ReturnCallbackService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    private ConfirmCallbackService confirmCallbackService;
    private ReturnCallbackService returnCallbackService;

    @Autowired
    public void setConfirmCallbackService(ConfirmCallbackService confirmCallbackService) {
        this.confirmCallbackService = confirmCallbackService;
    }

    @Autowired
    public void setReturnCallbackService(ReturnCallbackService returnCallbackService) {
        this.returnCallbackService = returnCallbackService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
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
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        //amqpAdmin.declareQueue(new Queue(metadataHeader.getRoutingKey(), true, false, false));
        //System.out.println("-----------------------------------------------------"+MessageConverter.toMessage((CloudEventV1) cloudEvent).getMessageProperties().getDelay());
        cloudEventFlux.limitRate(1000).subscribe(cloudEvent -> {
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), MessageConverter.toMessage((CloudEventV1) cloudEvent));
        });
    }

}
