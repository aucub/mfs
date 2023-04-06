package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import cn.hutool.core.net.UserPassAuthenticator;
import io.rsocket.metadata.CompositeMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Payloads;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
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
    public Mono<Void> connect(RSocketRequester requester, @Headers MessageHeaders messageHeaders) {
        System.out.println(messageHeaders.toString());
        for(Map.Entry<String,Object> entry:messageHeaders.entrySet()){
            System.out.println(entry.getKey()+"---------"+entry.getValue());
        }
        messageHeaders.get("dataBufferFactory");
        requester.rsocket()
                .onClose()
                .subscribe();
        return Mono.empty();
    }
    @ConnectMapping("connect1")
    public Mono<Void> connect1(RSocketRequester requester, @Headers Map<String, Object> metadata) {
        System.out.println(metadata.containsKey("traceId"));
        for(Map.Entry<String,Object> entry:metadata.entrySet()){
            System.out.println(entry.getKey()+"---------"+entry.getValue());
        }
        requester.rsocket()
                .onClose()
                .subscribe();
        return Mono.empty();
    }
}
