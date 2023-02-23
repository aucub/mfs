package cn.edu.zut.mfs.RabbitMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class RabbitMQReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQReceiver.class);

    private final Receiver receiver;
    private final Sender sender;

    public RabbitMQReceiver() {
        this.receiver = RabbitFlux.createReceiver();
        this.sender = RabbitFlux.createSender();
    }

    public Disposable consume(String queue, CountDownLatch latch) {
        return receiver.consumeAutoAck(queue)
                .delaySubscription(sender.declareQueue(QueueSpecification.queue(queue)))
                .subscribe(m -> {
                    LOGGER.info("收到消息： {}", new String(m.getBody()));
                    latch.countDown();
                });
    }

    public void close() {
        this.sender.close();
        this.receiver.close();
    }

}

