package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishStreamService;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private static String stream = UUID.randomUUID().toString();
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/mfs")
            .build();

    private RabbitStreamTemplate rabbitStreamTemplate;

    @Override
    public void publish(ForwardMessage forwardMessage) {
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(50))
                .create();
        rabbitStreamTemplate = new RabbitStreamTemplate(env, stream);
        rabbitStreamTemplate.setProducerCustomizer((name, builder) -> builder.name("Producer"));
        rabbitStreamTemplate.convertAndSend(forwardMessage.toString());
    }
}
