package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Tag(name = "消息转发")
@Slf4j
@Controller
@MessageMapping("message")
public class RSocketController {

    /**
     * 此 @MessageMapping 旨在用于“请求/响应”样式。
     * 对于收到的每条消息，都会返回一条新消息，
     * 其中包含 ORIGIN=Server 和 INTERACTION=Request-Response。
     */
    @MessageMapping("request-response")
    public Mono<Message> requestResponse(String request) {
        log.info("收到请求-响应请求:" + request);
        // 创建一个消息并返回它
        return Mono.just(new Message());
    }

    /**
     * 此 @MessageMapping 旨在使用“即发即弃”样式。
     * 接收到新的 CommandRequest 时，不返回任何内容（void）
     */

    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(final Message request) {
        log.info("收到即发即弃请求： {}", request);
        return Mono.empty();
    }

    /**
     * 此 @MessageMapping 旨在用于“请求流”样式。
     * 当收到一个新的请求命令时，一个新的事件流被启动并返回给客户端。
     */
    @Operation(summary = "订阅消息")
    @MessageMapping("stream")
    Flux<String> stream(final String request) {
        log.info("收到流请求：{}", request);
        return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> "test");
    }

    /**
     * 此 @MessageMapping 旨在使用“频道”样式。
     * 传入流包含传出消息流的间隔设置（以秒为单位）。
     */

    @MessageMapping("channel")
    Flux<Message> channel(final Flux<Duration> settings) {
        log.info("收到频道请求...");
        return settings
                .doOnNext(setting -> log.info("频道频率设置为 {} 秒.", setting.getSeconds()))
                .doOnCancel(() -> log.warn("客户端取消了频道."))
                .switchMap(setting -> Flux.interval(setting)
                        .map(index -> new Message()));
    }
}

