package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.RSocketServer;
import cn.edu.zut.mfs.utils.MessageConverter;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {
    private static final Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    RSocketServer rSocketServer;
    private Consumer consumer;

    @Autowired
    public void setSocketServer(RSocketServer rSocketServer) {
        this.rSocketServer = rSocketServer;
    }

    @Override
    public Flux<CloudEventV1> consume(String userId, Consume consume) {
        if (environment.queryStreamStats(consume.getQueue()) == null) {
            environment.streamCreator()
                    .stream(consume.getQueue())
                    .maxLengthBytes(ByteCapacity.GB(1))
                    .maxSegmentSizeBytes(ByteCapacity.MB(100))
                    .create();
        }
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        OffsetSpecification offsetSpecification = OffsetSpecification.none();
        if (Boolean.TRUE.equals(Boolean.TRUE.equals(consume.getManual()))) {
            if (consume.getTimestamp() > 0) {
                offsetSpecification = OffsetSpecification.timestamp(consume.getTimestamp());
            } else if (consume.getOffset() == -1) {
                offsetSpecification = OffsetSpecification.last();
            } else if (consume.getOffset() >= 0) {
                offsetSpecification = OffsetSpecification.offset(consume.getOffset());
            }
        }
        OffsetSpecification finalOffsetSpecification = offsetSpecification;
        Flux<CloudEventV1> f = Flux.create(emitter -> {
            consumer =
                    environment.consumerBuilder()
                            .stream(consume.getQueue())
                            .name(userId)
                            .offset(finalOffsetSpecification)
                            .autoTrackingStrategy()
                            .messageCountBeforeStorage(50_000)
                            .flushInterval(Duration.ofSeconds(10))
                            .builder()
                            .messageHandler((context, message) -> {
                                        emitter.next(MessageConverter.fromStreamMessage(context, message));
                                        Thread.startVirtualThread(() -> {
                                            ConsumeRecord consumeRecord = new ConsumeRecord((String) message.getProperties().getMessageId(), message.getPublishingId(), context.offset(), consume.getQueue(), userId, Instant.now());
                                            influxDBService.consume(consumeRecord);
                                        });
                                    }
                            )
                            .build();
            emitter.onDispose(() -> consumer.close());
        });
        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> new CloudEventV1(UUID.randomUUID().toString(), URI.create("example.com/mfs"), "com.example.mfs", "text/plain", URI.create("mfs"), "mfs", Instant.now().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap("暂无新消息".getBytes()), null))
                .mergeWith(f);
    }
}
