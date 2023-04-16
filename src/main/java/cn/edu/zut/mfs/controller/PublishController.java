package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.PublishStreamService;
import cn.edu.zut.mfs.service.RequestProcessor;
import io.cloudevents.CloudEvent;
import io.cloudevents.spring.messaging.CloudEventMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class PublishController {

    private PublishStreamService publishStreamService;

    private PublishService publishService;

    private CloudEventMessageConverter cloudEventMessageConverter;

    private SimpleMessageConverter simpleMessageConverter;

    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Autowired
    public void setCloudEventMessageConverter(CloudEventMessageConverter cloudEventMessageConverter) {
        this.cloudEventMessageConverter = cloudEventMessageConverter;
    }

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Autowired
    public void setPublishStreamService(PublishStreamService publishStreamService) {
        this.publishStreamService = publishStreamService;
    }

    @MessageMapping("publish")
    public Flux<String> publish(RSocketRequester requester, @Headers Map<String, Object> metadata, Flux<CloudEvent> cloudEventFlux) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "publish");
        cloudEventFlux.limitRate(100).subscribe(item -> {
            if (metadataHeader.getQueueType().equals("stream")) {
                publishStreamService.publish(item, metadataHeader);
            } else {
                publishService.publish(item, metadataHeader);
            }
        });
        return Flux.interval(Duration.ofSeconds(1)).map(i -> UUID.randomUUID().toString());
    }
}
