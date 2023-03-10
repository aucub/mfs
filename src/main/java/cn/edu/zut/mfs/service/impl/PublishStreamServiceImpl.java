package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.rabbitmq.stream.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PublishStreamServiceImpl {
    private final static String stream = "mfs";
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private static Producer producer;
    private static RabbitStreamTemplate rabbitStreamTemplate = new RabbitStreamTemplate(env, "mfs");

    @BeforeAll
    public static void init() {
        rabbitStreamTemplate.setProducerCustomizer((name, builder) -> builder.name("test"));
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(5))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    public void publish(ForwardMessage forwardMessage) {
        producer = env.producerBuilder()
                .stream(stream)
                .name("mfs")
                .build();
        long nextPublishingId = producer.getLastPublishingId() + 1;
        Message message = producer.messageBuilder()
                .properties()
                .messageId(UUID.randomUUID())
                .correlationId(UUID.randomUUID())
                .contentType("text/plain")
                .messageBuilder()
                .addData(forwardMessage.getBody())
                .build();
        producer.send(message, confirmationStatus -> {
        });

    }

    public void consume() {
        Consumer consumer =
                env.consumerBuilder()
                        .stream(stream)
                        .name("application-1")
                        .offset(OffsetSpecification.first())
                        .autoTrackingStrategy()
                        .builder()
                        .messageHandler((context, message) -> {
                            System.out.println(message.getBody());
                        })
                        .build();
    }
}
