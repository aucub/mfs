package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.domain.MetadataHeader;
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
    public Mono<Void> connect(RSocketRequester requester, @Headers Map<String, Object> metadata) {
        /*MetadataHeader metadataHeader= (MetadataHeader) metadata.get("metadataHeader");
        if(StpUtil.getLoginIdByToken(metadataHeader.getToken())==null){
            throw new NotLoginException(null,null,null);
        }
        StpUtil.getTokenSessionByToken(metadataHeader.getToken());
        if(!StpUtil.hasPermission("message:connect")){
            throw new  NotPermissionException("message:connect");
        }
        System.out.println(metadataHeader);*/
        System.out.println(metadata);
       // System.out.println(metadata.get("topic"));
        //requestProcessor.processRequests(requester, metadataHeader.getClient());
        return Mono.empty();
    }
}
