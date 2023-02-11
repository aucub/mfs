package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.model.Message;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        log.info("Detaching all remaining clients...");
        CLIENTS.forEach(requester -> Objects.requireNonNull(requester.rsocket()).dispose());
        log.info("Shutting down.");
    }

    @ConnectMapping("shell-client")
    void connectShellClientAndAskForTelemetry(RSocketRequester requester,
                                              @Payload String client) {

        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    // 将所有新用户添加到用户列表
                    log.info("Client: {} CONNECTED.", client);
                    CLIENTS.add(requester);
                })
                .doOnError(error -> {
                    // 当用户关闭频道时发出警告
                    log.warn("Channel to client {} CLOSED", client);
                })
                .doFinally(consumer -> {
                    // 从客户端列表中删除断开连接的客户端
                    CLIENTS.remove(requester);
                    log.info("Client {} DISCONNECTED", client);
                })
                .subscribe();

        // 回调客户端，确认连接
        requester.route("client-status")
                .data("OPEN")
                .retrieveFlux(String.class)
                .doOnNext(s -> log.info("Client: {} Free Memory: {}.", client, s))
                .subscribe();
    }

    /**
     * 此 @MessageMapping 旨在用于“请求 --> 响应”样式。
     * 对于收到的每条消息，都会返回一条新消息，其中包含 ORIGIN=Server 和 INTERACTION=Request-Response。
     */
    @PreAuthorize("hasRole('USER')")
    @MessageMapping("request-response")
    Mono<Message> requestResponse(final Message request, @AuthenticationPrincipal UserDetails user) {
        log.info("Received request-response request: {}", request);
        log.info("Request-response initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
        // 创建一个消息并返回它
        return Mono.just(new Message(SERVER, RESPONSE));
    }

    /**
     * 此 @MessageMapping 旨在使用“fire --> forget”样式。
     * 接收到新的 CommandRequest 时，不返回任何内容（void）
     */
    @PreAuthorize("hasRole('USER')")
    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(final Message request, @AuthenticationPrincipal UserDetails user) {
        log.info("Received fire-and-forget request: {}", request);
        log.info("Fire-And-Forget initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
        return Mono.empty();
    }

    /**
     * 此 @MessageMapping 旨在用于“订阅 --> 流”样式。
     * 当收到一个新的请求命令时，一个新的事件流被启动并返回给客户端。
     */
    @PreAuthorize("hasRole('USER')")
    @MessageMapping("stream")
    Flux<Message> stream(final Message request, @AuthenticationPrincipal UserDetails user) {
        log.info("Received stream request: {}", request);
        log.info("Stream initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());

        return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> new Message(SERVER, STREAM, index));
    }

    /**
     * 此 @MessageMapping 旨在使用“stream <--> stream”样式。
     * 传入流包含传出消息流的间隔设置（以秒为单位）。
     */
    @PreAuthorize("hasRole('USER')")
    @MessageMapping("channel")
    Flux<Message> channel(final Flux<Duration> settings, @AuthenticationPrincipal UserDetails user) {
        log.info("Received channel request...");
        log.info("Channel initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());

        return settings
                .doOnNext(setting -> log.info("Channel frequency setting is {} second(s).", setting.getSeconds()))
                .doOnCancel(() -> log.warn("The client cancelled the channel."))
                .switchMap(setting -> Flux.interval(setting)
                        .map(index -> new Message(SERVER, CHANNEL, index)));
    }
}

