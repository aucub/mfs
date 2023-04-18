package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.ConfirmCallbackService;
import cn.edu.zut.mfs.service.PublishTaskService;
import cn.edu.zut.mfs.service.ReturnCallbackService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PublishTaskServiceImpl implements PublishTaskService {
    private final RabbitTemplate rabbitTemplate;

    private final TaskExecutor exec;
    private ConfirmCallbackService confirmCallbackService;
    private ReturnCallbackService returnCallbackService;

    @Autowired
    public PublishTaskServiceImpl(RabbitTemplate rabbitTemplate, TaskExecutor exec) {
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
        this.exec = exec;
    }

    @Autowired
    public void setConfirmCallbackService(ConfirmCallbackService confirmCallbackService) {
        this.confirmCallbackService = confirmCallbackService;
    }

    @Autowired
    public void setReturnCallbackService(ReturnCallbackService returnCallbackService) {
        this.returnCallbackService = returnCallbackService;
    }

    public void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), MessageConverter.toMessage((CloudEventV1) cloudEvent));
        });
    }
}
