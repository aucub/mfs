package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.PublishStreamService;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private static Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private RabbitStreamTemplate rabbitStreamTemplate;

    public static void stream(String stream) {
        environment.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    public void setRabbitStreamTemplate(String stream) {
        rabbitStreamTemplate = new RabbitStreamTemplate(environment, stream);
    }

    @Override
    public void publish(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        if (environment.queryStreamStats(metadataHeader.getRoutingKey()) == null) {
            stream(metadataHeader.getRoutingKey());
        }
        setRabbitStreamTemplate(metadataHeader.getRoutingKey());
        cloudEventFlux.subscribe(cloudEvent -> {
            String subject = "data";
            if (cloudEvent.getSubject() != null) {
                subject = cloudEvent.getSubject();
            }
            String contentType = "application/octet-stream";
            if (cloudEvent.getDataContentType() != null) {
                contentType = cloudEvent.getDataContentType();
            }
            String contentEncoding = "";
            if (cloudEvent.getExtension("contentencoding") != null) {
                contentEncoding = (String) cloudEvent.getExtension("contentencoding");
            }
            long creationTime = System.currentTimeMillis();
            if (cloudEvent.getTime() != null) {
                creationTime = cloudEvent.getTime().toInstant().toEpochMilli();
            }
            rabbitStreamTemplate.send(rabbitStreamTemplate.messageBuilder().properties().messageId(cloudEvent.getId()).contentType(contentType).contentEncoding(contentEncoding).subject(subject).creationTime(creationTime).messageBuilder().publishingId(Long.valueOf((String) cloudEvent.getExtension("publishingid"))).addData(cloudEvent.getData().toBytes()).build()).thenAccept(result -> {
                PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Long.valueOf((String) cloudEvent.getExtension("publishingid")), cloudEvent.getDataContentType(), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(cloudEvent.getData().toBytes()), result, cloudEvent.getTime().toInstant());
                influxDBService.publish(publishRecord);
            });
        });
    }
}
