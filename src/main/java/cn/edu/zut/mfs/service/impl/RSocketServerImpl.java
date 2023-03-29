package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.controller.MessageController;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.RSocketServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.rsocket.metadata.TaggingMetadataCodec;
import io.rsocket.util.ByteBufPayload;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static cn.dev33.satoken.SaManager.log;

public class RSocketServerImpl implements RSocketServer {

    @Override
    public void send(ForwardMessage forwardMessage) {
        if (MessageController.clients.containsKey(forwardMessage.getClient())) {
            RSocketRequester requester = MessageController.clients.get(forwardMessage.getClient());
            ByteBuf routeMetadata = TaggingMetadataCodec
                    .createTaggingContent(ByteBufAllocator.DEFAULT, Collections.singletonList("message"));

            Mono.just("Server is calling you.")
//                .delayElement(Duration.ofSeconds(ThreadLocalRandom.current().nextInt(5, 10)))
                    .flatMap(m -> requester.rsocketClient().requestResponse(
                                    Mono.just(
                                            ByteBufPayload.create(
                                                    ByteBufUtil.writeUtf8(
                                                            ByteBufAllocator.DEFAULT,
                                                            forwardMessage.toString()),
                                                    routeMetadata)))
                            .doOnSubscribe(subscription -> log.info("Client subscribed."))
                            .doOnError(throwable -> log.error("Error when calling client: {}", throwable.toString()))
                            .doOnSuccess(p -> log.info("[test.connect.requester]Received from client: {}.", p.getDataUtf8()))
                    )
                    .subscribe();
        }
    }
}
