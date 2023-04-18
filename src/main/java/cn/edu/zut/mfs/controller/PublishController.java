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

import java.util.Map;

@Slf4j
@RestController
public class PublishController {

    private PublishStreamService publishStreamService;

    private PublishService publishService;


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
    public Mono<Void> publish(RSocketRequester requester, @Headers Map<String, Object> metadata, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "publish");
        if (metadataHeader.getQueueType().equals("stream")) {
            publishStreamService.publish(cloudEventFlux, metadataHeader);
        } else {
            publishService.publish(cloudEventFlux, metadataHeader);
        }
        return Mono.empty();
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
