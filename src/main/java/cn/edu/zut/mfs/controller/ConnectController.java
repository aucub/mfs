package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import cn.edu.zut.mfs.utils.JwtUtils;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

    @PreAuthorize("hasRole('connect')")
    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester, @AuthenticationPrincipal String token, CloudEventV1 cloudEventV1) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "connect");
        return Mono.empty();
    }

}
