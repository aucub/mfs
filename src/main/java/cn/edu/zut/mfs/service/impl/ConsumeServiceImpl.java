package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {

    private static AmqpAdmin amqpAdmin;
    private ConnectionFactory connectionFactory;

    @Autowired
    public static void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        ConsumeServiceImpl.amqpAdmin = amqpAdmin;
    }

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Flux<CloudEventV1> consume(String userId, Consume consume) {
        if (amqpAdmin.getQueueInfo(consume.getQueue()) == null) amqpAdmin.deleteQueue(consume.getQueue());
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueueNames(consume.getQueue());
        Flux<CloudEventV1> f = Flux.create(emitter -> {
            simpleMessageListenerContainer.setupMessageListener(message -> {
                emitter.next(MessageConverter.fromMessage(message));
                Thread.startVirtualThread(() -> {
                    ConsumeRecord consumeRecord = new ConsumeRecord((String) message.getMessageProperties().getMessageId(), consume.getQueue(), userId, Instant.now());
                    influxDBService.consume(consumeRecord);
                });
            });
            emitter.onRequest(v -> {
                simpleMessageListenerContainer.start();
            });
            emitter.onDispose(() -> {
                simpleMessageListenerContainer.stop();
            });
        });
        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> new CloudEventV1(UUID.randomUUID().toString(), URI.create("http://example.com/mfs"), "com.example.mfs", "text/plain", URI.create("mfs"), "mfs", Instant.now().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap("暂无新消息".getBytes()), null))
                .mergeWith(f);
    }
}
