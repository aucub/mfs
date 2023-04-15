package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.PublishStreamService;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class PublishController {

    private PublishStreamService publishStreamService;

    private PublishService publishService;

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Autowired
    public void setPublishStreamService(PublishStreamService publishStreamService) {
        this.publishStreamService = publishStreamService;
    }

    @MessageMapping("publish")
    public Flux<String> publish(@Headers Map<String, Object> metadata, @Payload Flux<CloudEvent> cloudEventFlux) {
       // MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        //cloudEventV1Flux.limitRate(100).subscribe(item -> System.out.println(item.toString()));
        cloudEventFlux.subscribe(item->log.info(item.getId()));
        //return Mono.just("test");
        return Flux.interval(Duration.ofSeconds(1)).map(i-> UUID.randomUUID().toString());
    }
}
