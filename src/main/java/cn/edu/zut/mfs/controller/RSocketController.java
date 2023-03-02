package cn.edu.zut.mfs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * RSocket
 */
@Tag(name = "消息")
@Slf4j
@Controller
@MessageMapping("message")
public class RSocketController {

    /**
     * 当收到一个新的请求命令时，一个新的事件流被启动并返回给客户端。
     */
    @MessageMapping("subscribe")
    public Flux<String> subscribe() {
        log.info("收到流请求");
        return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(3))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> "test").log();
    }

    @MessageMapping("channel")
    Flux<String> channel(final Flux<Duration> settings) {
        log.info("Received channel request...");
        return settings
                .doOnNext(setting -> log.info("Channel frequency setting is {} second(s).", setting.getSeconds()))
                .doOnCancel(() -> log.warn("The client cancelled the channel."))
                .switchMap(setting -> Flux.interval(setting)
                        .map(index -> ""));
    }
}

