package cn.edu.zut.mfs.ReactorRabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class RabbitmqSamples {

    static final String QUEUE = "reactor.rabbitmq.spring.boot";
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqSamples.class);


    Mono<Connection> connectionMono;


    AmqpAdmin amqpAdmin;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSamples.class, args).close();
    }

    // 用于连接的Mono，它被缓存以在发送方和接收方实例之间重新使用连接这在大多数情况下应该可以正常工作
    @Bean()
    Mono<Connection> connectionMono(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return Mono.fromCallable(() -> connectionFactory.newConnection("reactor-rabbit")).cache();
    }

    @Bean
    Sender sender(Mono<Connection> connectionMono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }

    @Bean
    Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }

    @Bean
    Flux<Delivery> deliveryFlux(Receiver receiver) {
        return receiver.consumeNoAck(QUEUE);
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareQueue(new Queue(QUEUE, false, false, true));
    }

    @PreDestroy
    public void close() throws Exception {
        Objects.requireNonNull(connectionMono.block()).close();
    }

    // 使用发送方 bean 发布消息并使用接收方 bean 使用它们的运行器
    static class Runner implements CommandLineRunner {
        final Sender sender;
        final Flux<Delivery> deliveryFlux;
        final AtomicBoolean latchCompleted = new AtomicBoolean(false);

        Runner(Sender sender, Flux<Delivery> deliveryFlux) {
            this.sender = sender;
            this.deliveryFlux = deliveryFlux;
        }

        @Override
        public void run(String... args) throws Exception {
            int messageCount = 10;
            CountDownLatch latch = new CountDownLatch(messageCount);
            deliveryFlux.subscribe(m -> {
                LOGGER.info("收到消息： {}", new String(m.getBody()));
                latch.countDown();
            });
            LOGGER.info("发送消息中...");
            sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", QUEUE, ("Message_" + i).getBytes())))
                    .subscribe();
            latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
        }

    }


}
