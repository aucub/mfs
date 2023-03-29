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

    public final static HashMap<String, RSocketRequester> requesters = new HashMap<>();
    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester,
                              @Payload String client) {
        requestProcessor.processRequests(requester, client);
        return Mono.empty();
    }
}
