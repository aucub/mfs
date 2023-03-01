package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.FanoutMessage;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.RabbitMQService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class RabbitMQServiceImpl implements RabbitMQService {
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

    public void sender(ForwardMessage forwardMessage) throws InterruptedException {
        amqpAdmin.declareQueue(new Queue(forwardMessage.getQUEUE(), false, false, true));
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        log.info("Sending messages...");
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", forwardMessage.getQUEUE(), ("Message_" + i).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    public void receiver(ForwardMessage forwardMessage) throws InterruptedException {
        amqpAdmin.declareQueue(new Queue(forwardMessage.getQUEUE(), false, false, true));
        Flux<Delivery> deliveryFlux = receiver.consumeNoAck(forwardMessage.getQUEUE());
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        deliveryFlux.subscribe(m -> {
            log.info("Received message {}", new String(m.getBody()));
            latch.countDown();
        });
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    public void sendFanout(FanoutMessage fanoutMessage) throws InterruptedException {
        FanoutExchange fanoutExchange = new FanoutExchange(fanoutMessage.getFanoutExchangeName(), true, fanoutMessage.getAutoDelete());
        amqpAdmin.declareExchange(fanoutExchange);
        fanoutMessage.getConsumers().forEach(consumer -> {
            Queue queue = new Queue("", true, true, fanoutMessage.getAutoDelete());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
        });
        int messageCount = 10;
        CountDownLatch latch = new CountDownLatch(messageCount);
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage(fanoutExchange.getName(), "", ("Message_" + i).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
