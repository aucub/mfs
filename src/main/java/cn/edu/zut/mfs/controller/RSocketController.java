package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.RabbitMQService;
import com.rabbitmq.client.Delivery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * RSocket
 */
@Tag(name = "消息")
@Slf4j
@Controller
@MessageMapping("message")
public class RSocketController {
    @Autowired
    private RabbitMQService rabbitMQService;

    private final List<RSocketRequester> CLIENTS = new ArrayList<>();


    @ConnectMapping("connect")
    public void connect(RSocketRequester requester,
                        @Payload String client) {

        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", client);
                    CLIENTS.add(requester);
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", client))
                .doFinally(consumer -> {
                    CLIENTS.remove(requester);
                    log.info("客户端： {} 断开连接", client);
                })
                .subscribe();
    }


    /**
     * 当收到一个新的请求命令时，一个新的事件流被启动并返回给客户端。
     */
    @MessageMapping("stream")
    public Flux<Delivery> stream(ForwardMessage forwardMessage) {
        log.info("收到流请求");
        try {
            return rabbitMQService.receiver(forwardMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> "test" + index);*/

    }

    @MessageMapping("channel")
    Flux<String> channel(final Flux<ForwardMessage> msg) {
        return msg
                .doOnNext(in -> {
                    log.info("收到消息：  {}", in);
                    try {
                        rabbitMQService.sender(in);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(in -> "发送的消息：" + in);
    }

    @MessageMapping("fanoutChannel")
    Flux<String> fanoutChannel(final Flux<ForwardMessage> msg) {
        return msg
                .doOnNext(in -> {
                    log.info("收到消息：  {}", in);
                    try {
                        rabbitMQService.createFanout(in);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(in -> "发送的消息：" + in);
    }

}

