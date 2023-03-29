package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.ConsumeStreamService;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {

    private Consumer consumer;
    private final static Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();

    @Override
    public void consume(String stream,String client) {
        consumer =
                environment.consumerBuilder()
                        .stream(stream)
                        .name(client)
                        .offset(OffsetSpecification.first())
                        .autoTrackingStrategy()
                        .builder()
                        .messageHandler((context, message) -> {
                            System.out.println(message.getBody());
                        })
                        .build();
        consumer.storedOffset();
        try {
            wait(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
