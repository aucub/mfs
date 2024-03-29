package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ConnectController {
    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }


    /**
     * 连接
     *
     * @param requester 请求者
     * @param jwt       jwt
     * @return {@link Mono}<{@link Void}>
     */
    @PreAuthorize("hasRole('connect')")
    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester, @AuthenticationPrincipal Jwt jwt) {
        requestProcessor.processRequests(requester, jwt.getSubject(), "connect");
        return Mono.empty();
    }

}
