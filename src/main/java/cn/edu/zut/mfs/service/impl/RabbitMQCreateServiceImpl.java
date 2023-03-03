package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.RabbitMQCreateService;
import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class RabbitMQCreateServiceImpl implements RabbitMQCreateService {
    final Sender sender;
    final Receiver receiver;
    final AtomicBoolean latchCompleted = new AtomicBoolean(false);
    final AmqpAdmin amqpAdmin;
    final Mono<Connection> connectionMono;

    @Autowired
    public RabbitMQCreateServiceImpl(Sender sender, Receiver receiver, AmqpAdmin amqpAdmin, Mono<Connection> connectionMono) {
        this.sender = sender;
        this.receiver = receiver;
        this.amqpAdmin = amqpAdmin;
        this.connectionMono = connectionMono;
    }


    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
