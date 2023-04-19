package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.PublishStreamService;
import cn.edu.zut.mfs.service.QuestService;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private RabbitStreamTemplate rabbitStreamTemplate;
    private QuestService questService;

    public void setRabbitStreamTemplate(String stream) {
        rabbitStreamTemplate = new RabbitStreamTemplate(environment, stream);
    }

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    public void stream(String stream) {
        environment.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
    }

    @Override
    public void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        stream(metadataHeader.getRoutingKey());
        setRabbitStreamTemplate(metadataHeader.getRoutingKey());
        cloudEventFlux.subscribe(cloudEvent -> {//.limitRate(10000)
            Thread.startVirtualThread(() -> {
                rabbitStreamTemplate.send(rabbitStreamTemplate.messageBuilder().publishingId(Long.valueOf((String) cloudEvent.getExtension("publishingid"))).addData(cloudEvent.getData().toBytes()).build());
                //questService.publish(new PublishRecord(cloudEvent.getId(), (String) cloudEvent.getExtension("appid"), metadataHeader.getUserId(), 0, "", "", metadataHeader.getExchange(), 0, Long.valueOf((String) cloudEvent.getExtension("publishingid")), metadataHeader.getRoutingKey(), "stream", 0, cloudEvent.getData().toBytes()));
            });
        });
    }
}
