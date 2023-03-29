package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
@RestController
public class ConnectController {

    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public final static HashMap<String, RSocketRequester> requesters = new HashMap<>();

    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester,
                              @Payload String client) {
        requestProcessor.processRequests(requester, client);
        return Mono.empty();
    }
}
