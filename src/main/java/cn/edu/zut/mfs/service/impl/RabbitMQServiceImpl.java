package cn.edu.zut.mfs.service.impl;

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
        amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), false, false, true));
        int messageCount = 1;
        CountDownLatch latch = new CountDownLatch(messageCount);
        log.info("Sending messages...");
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", forwardMessage.getRoutingKey(), ("Message:" + forwardMessage.getContent()).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    public Flux<Delivery> receiver(ForwardMessage forwardMessage) throws InterruptedException {
        //amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), false, false, true));
        Flux<Delivery> deliveryFlux = receiver.consumeNoAck(forwardMessage.getQueue());
        int messageCount = 2;
        CountDownLatch latch = new CountDownLatch(messageCount);
        /*deliveryFlux.subscribe(m -> {
            log.info("Received message {}", new String(m.getBody()));
            latch.countDown();
        });*/
        return deliveryFlux;
        /*latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
        return null;*/
    }

    @Override
    public void createFanout(ForwardMessage forwardMessage) throws InterruptedException {
        FanoutExchange fanoutExchange = new FanoutExchange(forwardMessage.getExchange(), true, forwardMessage.getAutoDelete());
        amqpAdmin.declareExchange(fanoutExchange);
        forwardMessage.getConsumers().forEach(consumer -> {
            Queue queue = new Queue(consumer, true, true, forwardMessage.getAutoDelete());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
        });
        int messageCount = 1;
        CountDownLatch latch = new CountDownLatch(messageCount);
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage(fanoutExchange.getName(), "", ("Message_" + forwardMessage.getContent()).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
