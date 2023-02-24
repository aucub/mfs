package cn.edu.zut.mfs.RabbitMQ;

import cn.edu.zut.mfs.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
public class RabbitMQSender {


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);
    final AtomicBoolean latchCompleted = new AtomicBoolean(false);
    private final Sender sender;

    public RabbitMQSender() {
        this.sender = RabbitFlux.createSender();
    }

    public void send(String queue, CountDownLatch latch, Message message) {
        Flux<OutboundMessageResult> confirmations = sender.sendWithPublishConfirms(Flux.range(1, message.getCount())
                .map(i -> new OutboundMessage("", message.getQueue(), message.getRabbitMQMessages().get(i).getContent().getBytes())));

        sender.declareQueue(QueueSpecification.queue(queue))
                .thenMany(confirmations)
                .doOnError(e -> LOGGER.error("发送失败", e))
                .subscribe(r -> {
                    if (r.isAck()) {
                        LOGGER.info("消息 {} 发送成功", new String(r.getOutboundMessage().getBody()));
                        latch.countDown();
                    }
                });
    }

    public void close() {
        this.sender.close();
    }

}