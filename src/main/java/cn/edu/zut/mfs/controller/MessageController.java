package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Event;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.PublishStreamService;
import cn.edu.zut.mfs.service.impl.ConsumeStreamServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Objects;

/**
 * 消息处理
 */
@Slf4j
@RestController
@MessageMapping("Message")
public class MessageController {
    public final static HashMap<String,RSocketRequester > clients = new HashMap<>();
    private PublishStreamService publishStreamService;
    private PublishService publishService;
    //private ConsumeService consumeService;

    ConsumeStreamServiceImpl consumeStreamService;

    @Autowired
    public void setConsumeStreamService(ConsumeStreamServiceImpl consumeStreamService) {
        this.consumeStreamService = consumeStreamService;
    }

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    /*@Autowired
    public void setConsumeService(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }
*/
    @Autowired
    public void setPublishStreamService(PublishStreamService publishStreamService) {
        this.publishStreamService = publishStreamService;
    }

    @Operation(summary = "客户端连接")
    @ConnectMapping("connect")
    public Mono<Void> connect(RSocketRequester requester,
                        @Payload String client) {

        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", client);
                    clients.put( client,requester);
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", client))
                .doFinally(consumer -> {
                    clients.remove(client);
                    log.info("客户端： {} 断开连接", client);
                })
                .subscribe();
        return Mono.empty();
    }

    /*@MessageMapping("publish")
    public Mono<String> publish(Event event) {
        log.info("收到事件，客户端:" +event.getClient() + ",messageId:" + event.getId() );
        publishService.publish(event);
        return Mono.just("messageId:" + event.getId() + "已投递");
    }*/

    @Operation(summary = "生产")
    @MessageMapping("publish")
    public Mono<String> publish(ForwardMessage forwardMessage) {
        log.info("收到publisher:" + forwardMessage.getPublisher() + ",messageId:" + forwardMessage.getMessageId() + "消息");
        publishStreamService.publish(forwardMessage);
        return Mono.just("messageId:" + forwardMessage.getMessageId() + "已投递");
    }

   /* @Operation(summary = "消费")
    @MessageMapping("consume")
    public BaseResponse<ForwardMessage> consume(String consumer) {
        log.info("收到消费请求consumer:" + consumer);
        return BaseResponse.success(consumeService.consume(consumer));
    }*/

   @MessageMapping("consume")
   public void consume() {
       log.info("收到消费请求consume");
       consumeStreamService.consume();
   }

}

