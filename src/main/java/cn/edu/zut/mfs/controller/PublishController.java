package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.*;
import cn.edu.zut.mfs.utils.JwtUtils;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

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
    public Flux<String> publish(RSocketRequester requester, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "publish");
        cloudEventFlux.subscribe(event -> {
            if (event.getExtension("messagetype").equals("generic")) {
                publishStreamService.publish(event);
            } else {
                publishService.publish(event);
            }
        });
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @MessageMapping("publishTask")
    public Flux<String> publishTask(RSocketRequester requester, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "publish");
        publishTaskService.publish(cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }

    @MessageMapping("publishBatch")
    public Flux<String> publishBatch(RSocketRequester requester, @AuthenticationPrincipal String token, Flux<CloudEvent> cloudEventFlux) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "publish");
        publishBatchService.setup(100);
        publishBatchService.sendMessage(cloudEventFlux);
        return Flux.interval(Duration.ofSeconds(5)).map(i -> "OK");
    }
}
