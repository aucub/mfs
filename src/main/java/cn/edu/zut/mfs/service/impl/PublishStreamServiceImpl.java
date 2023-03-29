package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishStreamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.rabbit.stream.support.StreamMessageProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private RabbitStreamTemplate rabbitStreamTemplate;

    public void setRabbitStreamTemplate(String stream) {
        rabbitStreamTemplate = new RabbitStreamTemplate(environment, stream);
    }

    public void stream(String stream) {
        environment.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    @Override
    public void publish(ForwardMessage forwardMessage) {
        StreamMessageProperties streamMessageProperties = new StreamMessageProperties();
        try {
            stream(forwardMessage.getTopic());
            setRabbitStreamTemplate(forwardMessage.getTopic());
            rabbitStreamTemplate.send(new org.springframework.amqp.core.Message(mapper.writeValueAsBytes(forwardMessage), streamMessageProperties));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
