package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.TaskExecutor;
import reactor.core.publisher.Flux;

public class PublishTaskServiceImpl {
    private final RabbitTemplate rabbitTemplate;

    private final TaskExecutor exec;

    public PublishTaskServiceImpl(RabbitTemplate rabbitTemplate, TaskExecutor exec) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setUsePublisherConnection(true);
        this.exec = exec;
    }

    public void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), MessageConverter.toMessage((CloudEventV1) cloudEvent));
        });
    }
}
