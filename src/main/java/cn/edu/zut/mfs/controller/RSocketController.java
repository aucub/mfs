package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.PublishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

/**
 * RSocket
 */
@Tag(name = "RSocket")
@Slf4j
@RestController
@MessageMapping("rSocket")
public class RSocketController {
    private final HashMap<RSocketRequester, String> clients = new HashMap<>();
    private PublishService publishService;
    private ConsumeService consumeService;

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Autowired
    public void setConsumeService(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }

    @Operation(summary = "客户端连接")
    @ConnectMapping("connect")
    public void connect(RSocketRequester requester,
                        @Payload String client) {

        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", client);
                    clients.put(requester, client);
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", client))
                .doFinally(consumer -> {
                    clients.remove(requester);
                    log.info("客户端： {} 断开连接", client);
                })
                .subscribe();
    }

    @Operation(summary = "生产")
    @MessageMapping("publish")
    public BaseResponse<String> publish(ForwardMessage forwardMessage) {
        log.info("收到生产请求");
        publishService.publish(forwardMessage);
        return BaseResponse.success("");
    }

    @Operation(summary = "消费")
    @MessageMapping("consume")
    public BaseResponse<String> consume(String consumer) {
        log.info("收到流请求");
        return BaseResponse.success(consumeService.consume(consumer));
    }

}

