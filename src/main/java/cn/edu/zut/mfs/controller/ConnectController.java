package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.service.RequestProcessor;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 连接控制器
 */
@Slf4j
@RestController
public class ConnectController {
    private RequestProcessor requestProcessor;
    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester, @Headers Map<String, Object> metadata, CloudEventV1 cloudEventV1) {
        MetadataHeader metadataHeader = (MetadataHeader) metadata.get("metadataHeader");
        requestProcessor.processRequests(requester, metadataHeader.getUserId(), "connect");
        return Mono.empty();
    }

}
