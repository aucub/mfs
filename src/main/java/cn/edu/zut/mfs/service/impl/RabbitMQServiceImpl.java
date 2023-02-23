package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.RabbitMQ.RabbitMQReceiver;
import cn.edu.zut.mfs.RabbitMQ.RabbitMQSender;
import cn.edu.zut.mfs.model.Message;
import cn.edu.zut.mfs.service.RabbitMQService;
import cn.hutool.core.lang.UUID;
import lombok.SneakyThrows;
import reactor.core.Disposable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class RabbitMQServiceImpl implements RabbitMQService {
    RabbitMQSender sender = new RabbitMQSender();

    @SneakyThrows
    public void Sender(Message message) {
        CountDownLatch latch = new CountDownLatch(message.getCount());
        String QUEUE = UUID.randomUUID().toString();
        final AtomicBoolean latchCompleted = new AtomicBoolean(false);
        sender.send(QUEUE, latch, message);
        //latch.await(10, TimeUnit.SECONDS);
        latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
        sender.close();
    }

    @SneakyThrows
    public void Receiver(String QUEUE, int count) {
        CountDownLatch latch = new CountDownLatch(count);
        RabbitMQReceiver receiver = new RabbitMQReceiver();
        Disposable disposable = receiver.consume(QUEUE, latch);
        latch.await(10, TimeUnit.SECONDS);
        disposable.dispose();
        receiver.close();
    }
}
