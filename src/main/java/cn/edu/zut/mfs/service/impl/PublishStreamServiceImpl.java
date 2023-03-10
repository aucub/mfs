package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishStreamService;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.rabbit.stream.support.StreamMessageProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private final static String stream = "mfs";
    private static final Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private static final RabbitStreamTemplate rabbitStreamTemplate = new RabbitStreamTemplate(env, "mfs");

    public PublishStreamServiceImpl() {
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(5))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    public void publish(ForwardMessage forwardMessage) {
        rabbitStreamTemplate.setProducerCustomizer((name, builder) -> builder.name("mfs"));
        StreamMessageProperties streamMessageProperties = new StreamMessageProperties();
        rabbitStreamTemplate.send(new org.springframework.amqp.core.Message(forwardMessage.getBody(), streamMessageProperties));
    }
}
