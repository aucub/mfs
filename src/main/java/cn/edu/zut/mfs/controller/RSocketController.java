package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.RabbitMQ.RabbitMQStream;
import cn.edu.zut.mfs.domain.Message;
import com.rabbitmq.client.Delivery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private static RabbitMQStream rabbitMQStream;

    @BeforeAll
    public static void startConsume() {
        try {
            rabbitMQStream.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setRabbitMQStream(RabbitMQStream rabbitMQStream) {
        this.rabbitMQStream = rabbitMQStream;
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
    public Mono<Long> publish(Message message) {
        log.info("收到生产请求");
        try {
            rabbitMQStream.publish(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Operation(summary = "消费")
    @MessageMapping("consume")
    public Flux<Delivery> consume() {
        log.info("收到流请求");
        return null;
        /*return Flux
                // 创建一个新的索引 Flux 每秒发射一个元素
                .interval(Duration.ofSeconds(1))
                // 使用索引的 Flux 创建新消息的 Flux
                .map(index -> "test" + index);
*/
    }

}

