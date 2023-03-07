package cn.edu.zut.mfs.RabbitMQ;

import cn.edu.zut.mfs.domain.Message;
import com.rabbitmq.stream.*;
import com.rabbitmq.stream.compression.Compression;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
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


    public long publish(Message message) throws InterruptedException {
        CountDownLatch publishConfirmLatch = new CountDownLatch(1);
        Producer producer = environment.producerBuilder()
                .name("mfs-producer")
                .confirmTimeout(Duration.ZERO)
                .compression(Compression.ZSTD)
                .stream(stream)
                .build();
        long nextPublishingId = producer.getLastPublishingId();
        producer.send(
                producer.messageBuilder()
                        .publishingId(nextPublishingId + 1)
                        .properties()
                        .messageId(UUID.randomUUID())
                        .correlationId(UUID.randomUUID())
                        .contentType("text/plain")
                        .messageBuilder()
                        .addData(message.getBody())
                        .build(),
                confirmationStatus -> publishConfirmLatch.countDown());
        publishConfirmLatch.await(10, TimeUnit.SECONDS);
        producer.close();
        return publishConfirmLatch.getCount();
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
