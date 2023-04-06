package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.PublishStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class PublishController {

    private PublishStreamService publishStreamService;

    private PublishService publishService;

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Autowired
    public void setPublishStreamService(PublishStreamService publishStreamService) {
        this.publishStreamService = publishStreamService;
    }


    @MessageMapping("publish1")
    public Mono<String> publish(Mono<String> body) {
        System.out.println(body);
        return body;
    }

    @MessageMapping("publish")
    public Mono<String> publish(@Payload ForwardMessage forwardMessage) {
        publishService.publish(forwardMessage);
        return Mono.just("messageId:" + forwardMessage.getId() + "已投递");
    }

    @MessageMapping("publishStream")
    public Mono<String> publishStream(ForwardMessage forwardMessage) {
        publishStreamService.publish(forwardMessage);
        return Mono.just("messageId:" + forwardMessage.getId() + "已投递");
    }
}
