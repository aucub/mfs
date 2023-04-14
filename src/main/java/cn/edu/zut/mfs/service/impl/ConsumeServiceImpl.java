package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.service.ConsumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private final static ObjectMapper mapper = new ObjectMapper();
    private RabbitTemplate rabbitTemplate;

    private ConnectionFactory connectionFactory;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Flux<byte[]> consume(Consume consume) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueueNames(consume.getQueue());
        Flux<byte[]> f = Flux.<byte[]>create(emitter -> {
            simpleMessageListenerContainer.setupMessageListener(message -> {
                emitter.next(message.getBody());
            });
            emitter.onRequest(v -> {
                simpleMessageListenerContainer.start();
            });
            emitter.onDispose(() -> {
                simpleMessageListenerContainer.stop();
            });
        });
        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> "No news is good news".getBytes())
                .mergeWith(f);
    }
}
