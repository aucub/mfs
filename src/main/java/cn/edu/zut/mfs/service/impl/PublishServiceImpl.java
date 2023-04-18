package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.QuestService;
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
    private QuestService questService;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    @Override
    @SneakyThrows
    public void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        cloudEventFlux.limitRate(1000).subscribe(cloudEvent -> {
            Thread.startVirtualThread(() -> {
                Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
                rabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
                questService.publish(new PublishRecord(cloudEvent.getId(), message.getMessageProperties().getAppId(), message.getMessageProperties().getUserId(), message.getMessageProperties().getPriority(), message.getMessageProperties().getCorrelationId(), message.getMessageProperties().getExpiration(), metadataHeader.getExchange(), message.getMessageProperties().getDelay(), 0, metadataHeader.getRoutingKey(), "classic", 0, cloudEvent.getData().toBytes()));
            });
        });
    }

}
