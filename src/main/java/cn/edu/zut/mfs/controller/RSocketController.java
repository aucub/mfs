package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequestMapping("/rsocket/")
@Tag(name = "消息转发")
@Slf4j
@Controller
public class RSocketController {

    public static final String SERVER = "Server";
    public static final String RESPONSE = "Response";
    public static final String STREAM = "Stream";
    public static final String CHANNEL = "Channel";

    private final List<RSocketRequester> CLIENTS = new ArrayList<>();

    @PreDestroy
    void shutdown() {
        log.info("分离所有剩余的客户端...");
        CLIENTS.forEach(requester -> Objects.requireNonNull(requester.rsocket()).dispose());
        log.info("关机.");
    }

    @Operation(summary = "客户端连接")
    @ConnectMapping("client")
    void connectClientAndAskForTelemetry(RSocketRequester requester,
                                         @Payload String client) {
        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    // 将新客户端添加到客户端列表
                    log.info("客户端: {} 连接.", client);
                    CLIENTS.add(requester);
                })
                .doOnError(error -> {
                    // 当客户端关闭频道时发出警告
                    log.warn("到客户端 {} 的频道关闭", client);
                })
                .doFinally(consumer -> {
                    // 从客户端列表中删除断开连接的客户端
                    CLIENTS.remove(requester);
                    log.info("客户端 {} 断开连接", client);
                })
                .subscribe();
        // 回调客户端，确认连接
        requester.route("client-status")
                .data("OPEN")
                .retrieveFlux(String.class)
                .doOnNext(s -> log.info("客户端: {} Free Memory: {}.", client, s))
                .subscribe();
    }

    /**
     * 此 @MessageMapping 旨在用于“请求/响应”样式。
     * 对于收到的每条消息，都会返回一条新消息，
     * 其中包含 ORIGIN=Server 和 INTERACTION=Request-Response。
     */
    @MessageMapping("request-response")
    Mono<Message> requestResponse(final Message request) {
        log.info("收到请求-响应请求: {}", request);
        // 创建一个消息并返回它
        return Mono.just(new Message(SERVER, RESPONSE));
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

    @MessageMapping("stream")
    Flux<Message> stream(final Message request) {
        log.info("收到流请求：{}", request);
        return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> new Message(SERVER, STREAM, index));
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
                        .map(index -> new Message(SERVER, CHANNEL, index)));
    }
}

