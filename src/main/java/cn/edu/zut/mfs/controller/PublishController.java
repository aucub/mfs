package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.*;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class PublishController {

    private PublishStreamService publishStreamService;

    private PublishService publishService;

    public static AtomicInteger atomicInteger = new AtomicInteger(0);


    private PublishBatchService publishBatchService;

    private PublishTaskService publishTaskService;

    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Autowired
    public void setPublishStreamService(PublishStreamService publishStreamService) {
        this.publishStreamService = publishStreamService;
    }

    @Autowired
    public void setPublishTaskService(PublishTaskService publishTaskService) {
        this.publishTaskService = publishTaskService;
    }

    @Autowired
    public void setPublishBatchService(PublishBatchService publishBatchService) {
        this.publishBatchService = publishBatchService;
    }

    @MessageMapping("publish")
    public Flux<String> publish(RSocketRequester requester, @Headers Map<String, Object> metadata, Flux<CloudEvent> cloudEventFlux) {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 100 == 0) {
            System.out.println(atomicInteger.get());
            log.error(String.valueOf(atomicInteger.get()));
        }
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "publish");
        if (metadataHeader.getQueueType().equals("stream")) {
            publishStreamService.publish(cloudEventFlux, metadataHeader);
        } else {
            publishService.publish(cloudEventFlux, metadataHeader);
        }
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @MessageMapping("publishTask")
    public Mono<Void> publishTask(RSocketRequester requester, @Headers Map<String, Object> metadata, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "publish");
        publishTaskService.publish(cloudEventFlux, metadataHeader);
        return Mono.empty();
    }

    @MessageMapping("publishBatch")
    public Mono<Void> publishBatch(RSocketRequester requester, @Headers Map<String, Object> metadata, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "publish");
        publishBatchService.setup(metadataHeader.getBatchSize());
        publishBatchService.sendMessage(cloudEventFlux, metadataHeader);
        return Mono.empty();
    }
}
