package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.ConsumeStreamService;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.MessageHandler;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {
    private final static String stream = "mfs";
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private RabbitStreamTemplate rabbitStreamTemplate;

    @Autowired
    public void setRabbitStreamTemplate(RabbitStreamTemplate rabbitStreamTemplate) {
        this.rabbitStreamTemplate = rabbitStreamTemplate;
    }

    @Override
    @RabbitListener(queues = "mfs", containerFactory = "nativeFactory")
    public void consumer(Message in, MessageHandler.Context context) {
        in.getBody();
        context.storeOffset();
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
