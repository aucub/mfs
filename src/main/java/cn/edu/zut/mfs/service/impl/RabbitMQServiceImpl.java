package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.RabbitMQ.RabbitMQ;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class RabbitMQServiceImpl {
    static final String QUEUE = "reactor.rabbitmq.spring.boot";
    final Sender sender;
    final Flux<Delivery> deliveryFlux;
    final AtomicBoolean latchCompleted = new AtomicBoolean(false);
    final
    AmqpAdmin amqpAdmin;
    RabbitMQ rabbitMQ = new RabbitMQ();
    Mono<Connection> connectionMono = rabbitMQ.connectionMono();

    @Autowired
    RabbitMQServiceImpl(Sender sender, Flux<Delivery> deliveryFlux, RabbitMQ rabbitMQ, Mono<Connection> connectionMono, AmqpAdmin amqpAdmin) {
        this.sender = sender;
        this.deliveryFlux = deliveryFlux;
        this.rabbitMQ = rabbitMQ;
        this.connectionMono = connectionMono;
        this.amqpAdmin = amqpAdmin;
    }

    public void test() throws InterruptedException {
        amqpAdmin.declareQueue(new Queue(QUEUE, false, false, true));
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        deliveryFlux.subscribe(m -> {
            log.info("Received message {}", new String(m.getBody()));
            latch.countDown();
        });
        log.info("Sending messages...");
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", QUEUE, ("Message_" + i).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
