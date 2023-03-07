package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Message;
import cn.edu.zut.mfs.service.RabbitMQService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
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

    public void sender(Message message) throws InterruptedException {
        //amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), true, true, true));
        amqpAdmin.declareQueue();
        int messageCount = 1;
        CountDownLatch latch = new CountDownLatch(messageCount);
        log.info("Sending messages...");
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", message.getRoutingKey(), ("Message:" + message.getContent()).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    public Flux<Delivery> receiver(Message message) throws InterruptedException {
        //amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), false, false, true));
        Flux<Delivery> deliveryFlux = receiver.consumeNoAck(message.getQueue());
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
    public void createFanout(Message message) throws InterruptedException {
        FanoutExchange fanoutExchange = new FanoutExchange(message.getExchange(), true, message.getAutoDelete());
        amqpAdmin.declareExchange(fanoutExchange);
        message.getConsumers().forEach(consumer -> {
            Queue queue = new Queue(consumer, true, true, message.getAutoDelete());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
        });
        int messageCount = 1;
        CountDownLatch latch = new CountDownLatch(messageCount);
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage(fanoutExchange.getName(), "", ("Message_" + message.getContent()).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    @Override
    public void createTopic(Message message) throws InterruptedException {
        TopicExchange topicExchange = new TopicExchange(message.getExchange(), true, message.getAutoDelete());
        amqpAdmin.declareExchange(topicExchange);
        message.getConsumers().forEach(consumer -> {
            Queue queue = new Queue(consumer, true, true, message.getAutoDelete());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(message.getRoutingKey()));
        });
        int messageCount = 1;
        CountDownLatch latch = new CountDownLatch(messageCount);
        sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage(message.getExchange(), message.getRoutingKey(), ("Message_" + message.getContent()).getBytes())))
                .subscribe();
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
