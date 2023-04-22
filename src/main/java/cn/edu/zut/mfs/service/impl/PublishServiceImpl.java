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

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource().toString(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Integer.valueOf((String) cloudEvent.getExtension("priority")), (String) cloudEvent.getExtension("expiration"), Integer.valueOf((String) cloudEvent.getExtension("delay")), (String) cloudEvent.getExtension("dataContentType"), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(cloudEvent.getData().toBytes()), cloudEvent.getTime().toInstant());
            influxDBService.publish(publishRecord);
        });
    }

}
