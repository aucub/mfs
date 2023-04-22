package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.service.ConsumeBatchService;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Service
public class ConsumeBatchServiceImpl implements ConsumeBatchService {
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public static void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        ConsumeBatchServiceImpl.amqpAdmin = amqpAdmin;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Flux<CloudEventV1> consume(String userId, Consume consume) {
        if (amqpAdmin.getQueueInfo(consume.getQueue()) == null) amqpAdmin.deleteQueue(consume.getQueue());
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        return Flux.create(emitter -> {
            for (int i = 0; i < consume.getBatchSize(); i++) {
                Message message = rabbitTemplate.receive(consume.getQueue());
                emitter.next(MessageConverter.fromMessage(message));
                Thread.startVirtualThread(() -> {
                    ConsumeRecord consumeRecord = new ConsumeRecord((String) message.getMessageProperties().getMessageId(), consume.getQueue(), userId, Instant.now());
                    influxDBService.consume(consumeRecord);
                });
            }
            emitter.complete();
        });
    }
}
