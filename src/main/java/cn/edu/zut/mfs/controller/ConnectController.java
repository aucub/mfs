package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

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
    public Mono<Void> connect1(RSocketRequester requester, @Headers Map<String, Object> metadata) {
        System.out.println(metadata.get("traceId"));
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            System.out.println(entry.getKey() + "---------" + entry.getValue());
        }
        requester.rsocket()
                .onClose()
                .subscribe();
        return Mono.empty();
    }
}
