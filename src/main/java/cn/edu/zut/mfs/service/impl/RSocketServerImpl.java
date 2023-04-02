package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.RSocketServer;
import cn.edu.zut.mfs.service.RequestProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.rsocket.metadata.TaggingMetadataCodec;
import io.rsocket.util.ByteBufPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@Slf4j
public class RSocketServerImpl implements RSocketServer {

    @Override
    public void send(ForwardMessage forwardMessage) {
        if (RequestProcessor.requesters.containsKey(forwardMessage.getClient())) {
            RSocketRequester requester = RequestProcessor.requesters.get(forwardMessage.getClient());
            ByteBuf routeMetadata = TaggingMetadataCodec
                    .createTaggingContent(ByteBufAllocator.DEFAULT, Collections.singletonList("message"));

            Mono.just("服务端呼叫")
//                .delayElement(Duration.ofSeconds(ThreadLocalRandom.current().nextInt(5, 10)))
                    .flatMap(m -> requester.rsocketClient().requestResponse(
                                    Mono.just(
                                            ByteBufPayload.create(
                                                    ByteBufUtil.writeUtf8(
                                                            ByteBufAllocator.DEFAULT,
                                                            forwardMessage.toString()),
                                                    routeMetadata)))
                            .doOnSubscribe(subscription -> log.info("客户端订阅"))
                            .doOnError(throwable -> log.error("服务端呼叫失败 {}", throwable.toString()))
                            .doOnSuccess(p -> log.info("客户端返回: {}", p.getDataUtf8()))
                    )
                    .subscribe();
        }
    }
}
