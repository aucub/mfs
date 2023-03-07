package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.service.RabbitMQService;
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
    private RabbitMQService rabbitMQService;

    @Autowired
    public void setRabbitMQService(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
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



}

