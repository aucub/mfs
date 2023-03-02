package cn.edu.zut.mfs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * RSocket
 */
@Tag(name = "消息")
@Slf4j
@Controller
@MessageMapping("message")
public class RSocketController {

    private final List<RSocketRequester> CLIENTS = new ArrayList<>();


    @ConnectMapping("client")
    void connectShellClientAndAskForTelemetry(RSocketRequester requester,
                                              @Payload String client) {

        requester.rsocket()
                .onClose()
                .doFirst(() -> {
                    // Add all new clients to a client list
                    log.info("Client: {} CONNECTED.", client);
                    CLIENTS.add(requester);
                })
                .doOnError(error -> {
                    // Warn when channels are closed by clients
                    log.warn("Channel to client {} CLOSED", client);
                })
                .doFinally(consumer -> {
                    // Remove disconnected clients from the client list
                    CLIENTS.remove(requester);
                    log.info("Client {} DISCONNECTED", client);
                })
                .subscribe();
    }


    /**
     * 当收到一个新的请求命令时，一个新的事件流被启动并返回给客户端。
     */
    @MessageMapping("subscribe")
    public Flux<String> subscribe() {
        log.info("收到流请求");
        return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> new String("test" + index));

    }

    @MessageMapping("channel")
    Flux<String> channel(final Flux<String> msg) {
        return msg
                .doOnNext(in -> log.info("收到消息：  {}", in))
                .map(in -> {
                    return new String("发送的消息：" + in);
                });
    }

}

