package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.PublishStreamService;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private RabbitStreamTemplate rabbitStreamTemplate;

    public void setRabbitStreamTemplate(String stream) {
        rabbitStreamTemplate = new RabbitStreamTemplate(environment, stream);
    }

    public void stream(String stream) {
        environment.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    @Override
    public void publish(CloudEvent cloudEvent) {
        stream((String) cloudEvent.getExtension("routingkey"));
        setRabbitStreamTemplate((String) cloudEvent.getExtension("routingkey"));
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
        //PublishRecord publishRecord=new PublishRecord(cloudEvent.getId(), (String) cloudEvent.getExtension("appid"), metadataHeader.getUserId(), 0, "", "", metadataHeader.getExchange(), 0, Long.valueOf((String) cloudEvent.getExtension("publishingid")), metadataHeader.getRoutingKey(), "stream", 0, cloudEvent.getData().toBytes());
        rabbitStreamTemplate.send(rabbitStreamTemplate.messageBuilder().properties().messageId(cloudEvent.getId()).contentType(contentType).contentEncoding(contentEncoding).subject(subject).creationTime(creationTime).messageBuilder().publishingId(Long.valueOf((String) cloudEvent.getExtension("publishingid"))).addData(cloudEvent.getData().toBytes()).build());
    }
}
