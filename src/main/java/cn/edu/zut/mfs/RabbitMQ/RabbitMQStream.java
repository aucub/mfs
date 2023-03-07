package cn.edu.zut.mfs.RabbitMQ;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.rabbitmq.stream.*;
import com.rabbitmq.stream.compression.Compression;
import org.junit.jupiter.api.BeforeAll;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RabbitMQStream {
    static String stream = "mfs";
    static Environment environment;

    @BeforeAll
    public static void createEnvironment() {
        environment = Environment.builder()
                .uri("rabbitmq-stream://root:root@47.113.201.150:5552/mfs")
                .build();
        environment.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(50))
                .create();
    }


    public void send(Flux<ForwardMessage> forwardMessageFlux) throws InterruptedException {
        int messageCount = 10000;
        CountDownLatch publishConfirmLatch = new CountDownLatch(messageCount);
        Producer producer = environment.producerBuilder()
                .name("mfs-producer")
                .confirmTimeout(Duration.ZERO)
                .compression(Compression.ZSTD)
                .stream(stream)
                .build();
        long nextPublishingId = producer.getLastPublishingId();
        forwardMessageFlux.subscribe(i -> producer.send(
                producer.messageBuilder()
                        .publishingId(nextPublishingId + 1)
                        .properties()
                        .messageId(UUID.randomUUID())
                        .correlationId(UUID.randomUUID())
                        .contentType("text/plain")
                        .messageBuilder()
                        .addData(String.valueOf(i).getBytes())
                        .build(),
                confirmationStatus -> publishConfirmLatch.countDown()));
        publishConfirmLatch.await(10, TimeUnit.SECONDS);
        producer.close();
    }

    public void consume() throws InterruptedException {
        int messageCount = 10000;
        AtomicLong sum = new AtomicLong(0);
        CountDownLatch consumeLatch = new CountDownLatch(messageCount);
        Consumer consumer = environment.consumerBuilder()
                .name("mfs-consumer")
                .stream(stream)
                .offset(OffsetSpecification.first())
                .messageHandler((offset, message) -> {
                    sum.addAndGet(Long.parseLong(new String(message.getBodyAsBinary())));
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
