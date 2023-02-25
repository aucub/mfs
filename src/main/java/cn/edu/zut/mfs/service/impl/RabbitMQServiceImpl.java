package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.model.Message;
import cn.edu.zut.mfs.service.RabbitMQService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    static final String QUEUE = "reactor.rabbitmq.spring.boot";
    final Sender sender;
    final Receiver receiver;
    final AtomicBoolean latchCompleted = new AtomicBoolean(false);
    final AmqpAdmin amqpAdmin;
    final Mono<Connection> connectionMono;

    @Autowired
    public RabbitMQServiceImpl(Sender sender, Receiver receiver, AmqpAdmin amqpAdmin, Mono<Connection> connectionMono) {
        this.sender = sender;
        this.receiver = receiver;
        this.amqpAdmin = amqpAdmin;
        this.connectionMono = connectionMono;
    }

    public void sender() throws InterruptedException {
        amqpAdmin.declareQueue(new Queue(QUEUE, false, false, true));
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        log.info("Sending messages...");
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", QUEUE, ("Message_" + i).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    public void receiver(Message message) {
        amqpAdmin.declareQueue(new Queue(message.getQueue(), false, false, true));
        Flux<Delivery> deliveryFlux = receiver.consumeNoAck(message.getQueue());
        CountDownLatch latch = new CountDownLatch(message.getCount());
        deliveryFlux.subscribe(m -> {
            log.info("Received message {}", new String(m.getBody()));
            latch.countDown();
        });
        try {
            latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void close() throws Exception {
        connectionMono.block().close();
    }
}
