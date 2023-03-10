package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.rabbitmq.stream.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.rabbit.stream.support.StreamMessageProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishStreamServiceImpl {
    private final static String stream = "mfs";
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private static Producer producer;
    private static RabbitStreamTemplate rabbitStreamTemplate = new RabbitStreamTemplate(env, "mfs");

    public PublishStreamServiceImpl() {
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(5))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    public void publish(ForwardMessage forwardMessage) {
        rabbitStreamTemplate.setProducerCustomizer((name, builder) -> builder.name("test"));
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(5))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
        StreamMessageProperties streamMessageProperties = new StreamMessageProperties();
        rabbitStreamTemplate.send(new org.springframework.amqp.core.Message(forwardMessage.getBody(), streamMessageProperties));
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

    @RabbitListener(id = "mfs", queues = "mfs", containerFactory = "nativeFactory")
    public void nativeMsg(String in) {
        log.info(in.toString());
    }
}
