package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RabbitTemplate rabbitTemplate;

    private ConnectionFactory connectionFactory;
    private QuestService questService;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }


    @Override
    public Flux<CloudEventV1> consume(Consume consume) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueueNames(consume.getQueue());
        Flux<CloudEventV1> f = Flux.create(emitter -> {
            simpleMessageListenerContainer.setupMessageListener(message -> {
                emitter.next(MessageConverter.fromMessage(message));
                questService.consume(new ConsumeRecord(message.getMessageProperties().getMessageId(), 0, 0, consume.getQueue(), consume.getUserId()));
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
