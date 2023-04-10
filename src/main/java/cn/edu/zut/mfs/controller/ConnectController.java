package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RequestProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
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


    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester, @Headers Map<String, Object> metadata, CloudEventV1 cloudEventV1) {
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
        System.out.println(cloudEventV1.toString());
        /*System.out.println(cloudEvent);
        PojoCloudEventData<Location> cloudEventData = mapData(
                cloudEvent,
                PojoCloudEventDataMapper.from(mapper,Location.class)
        );*/
        //System.out.println(cloudEventData.getValue());
        // System.out.println(metadata.get("topic"));
        //requestProcessor.processRequests(requester, metadataHeader.getClient());
        return Mono.empty();
    }

    @ConnectMapping("connect1")
    public Mono<Void> connect1(RSocketRequester requester, @Headers Map<String, Object> metadata, @Payload byte[] body) {
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
        System.out.println(new String(body));
        /*System.out.println(cloudEvent);
        PojoCloudEventData<Location> cloudEventData = mapData(
                cloudEvent,
                PojoCloudEventDataMapper.from(mapper,Location.class)
        );*/
        //System.out.println(cloudEventData.getValue());
        // System.out.println(metadata.get("topic"));
        //requestProcessor.processRequests(requester, metadataHeader.getClient());
        return Mono.empty();
    }

    /*@Bean
    @Order(-1)
    public RSocketStrategiesCustomizer cloudEventsCustomizer() {
        return new RSocketStrategiesCustomizer() {
            @Override
            public void customize(RSocketStrategies.Builder strategies) {
                strategies.encoder(new CloudEventEncoder());
                strategies.decoder(new CloudEventDecoder());
            }
        };

    }*/
}
