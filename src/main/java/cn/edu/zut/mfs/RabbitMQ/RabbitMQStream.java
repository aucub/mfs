package cn.edu.zut.mfs.RabbitMQ;

import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import com.rabbitmq.stream.Producer;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class RabbitMQStream {
    String stream = UUID.randomUUID().toString();
    Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/mfs")
            .build();

    public void send() throws InterruptedException {
        int messageCount = 10000;
        CountDownLatch publishConfirmLatch = new CountDownLatch(messageCount);
        Producer producer = environment.producerBuilder()
                .stream(stream)
                .build();
        IntStream.range(0, messageCount)
                .forEach(i -> producer.send(
                        producer.messageBuilder()
                                .addData(String.valueOf(i).getBytes())
                                .build(),
                        confirmationStatus -> publishConfirmLatch.countDown()
                ));
        publishConfirmLatch.await(10, TimeUnit.SECONDS);
        producer.close();
        System.out.printf("Published %,d messages%n", messageCount);
    }

    public void consume() throws InterruptedException {
        int messageCount = 10000;
        AtomicLong sum = new AtomicLong(0);
        CountDownLatch consumeLatch = new CountDownLatch(messageCount);
        Consumer consumer = environment.consumerBuilder()
                .stream(stream)
                .offset(OffsetSpecification.first())
                .messageHandler((offset, message) -> {
                    sum.addAndGet(Long.parseLong(new String(message.getBodyAsBinary())));  // <4>
                    consumeLatch.countDown();
                })
                .build();

        consumeLatch.await(10, TimeUnit.SECONDS);

        System.out.println("Sum: " + sum.get());

        consumer.close();
    }

    public void closeEnvironment() {
        environment.deleteStream(stream);
        environment.close();
    }

}
