package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    public static void send(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux, InfluxDBService influxDBService, RabbitTemplate rabbitTemplate) {
        cloudEventFlux.limitRate(100).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            Thread.startVirtualThread(() -> {
                if (message.getMessageProperties().getDelay() == null)
                    message.getMessageProperties().setDelay(0);
                PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource().toString(), cloudEvent.getType(), message.getMessageProperties().getAppId(), userId, message.getMessageProperties().getPriority(), message.getMessageProperties().getExpiration(), message.getMessageProperties().getDelay(), message.getMessageProperties().getContentType(), message.getMessageProperties().getContentEncoding(), cloudEvent.getSubject(), new String(Objects.requireNonNull(cloudEvent.getData()).toBytes()), Objects.requireNonNull(cloudEvent.getTime()).toInstant());
                influxDBService.publish(publishRecord);
            });
        });
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        PublishServiceImpl.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        PublishBatchServiceImpl.check(metadataHeader, amqpAdmin);
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        send(userId, metadataHeader, cloudEventFlux, influxDBService, rabbitTemplate);
    }

}
