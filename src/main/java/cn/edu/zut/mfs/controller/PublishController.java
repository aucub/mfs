package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.*;
import cn.edu.zut.mfs.utils.JwtUtils;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
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
    @PreAuthorize("hasRole('publish')")
    public Flux<String> publish(RSocketRequester requester, @Headers Map<String, Object> metadata, @AuthenticationPrincipal Jwt jwt, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        // String userId = JwtUtils.decode(token).getSubject();
        String userId = jwt.getSubject();
        requestProcessor.processRequests(requester, userId, "publish");
        publishStreamService.publish(userId, metadataHeader, cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @PreAuthorize("hasRole('publish')")
    @MessageMapping("publishClassic")
    public Flux<String> publishClassic(RSocketRequester requester, @Headers Map<String, Object> metadata, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        String userId = JwtUtils.decode(token).getSubject();
        requestProcessor.processRequests(requester, userId, "publishClassic");
        publishService.publish(userId, metadataHeader, cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @PreAuthorize("hasRole('publish')")
    @MessageMapping("publishTask")
    public Flux<String> publishTask(RSocketRequester requester, @Headers Map<String, Object> metadata, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        String userId = JwtUtils.decode(token).getSubject();
        requestProcessor.processRequests(requester, userId, "publishTask");
        publishTaskService.publish(userId, metadataHeader, cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @PreAuthorize("hasRole('publish')")
    @MessageMapping("publishBatch")
    public Flux<String> publishBatch(RSocketRequester requester, @Headers Map<String, Object> metadata, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        String userId = JwtUtils.decode(token).getSubject();
        requestProcessor.processRequests(requester, userId, "publishTask");
        publishBatchService.setup(metadataHeader.getBatchSize());
        publishBatchService.sendMessage(userId, metadataHeader, cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }
}
