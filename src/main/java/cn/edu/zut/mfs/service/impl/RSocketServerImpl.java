package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.RSocketServer;
import cn.edu.zut.mfs.service.RequestProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
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
    public Boolean push(PushMessage pushMessage) {
        if (RequestProcessor.requesters.containsKey(pushMessage.getUserId())) {
            RSocketRequester requester = RequestProcessor.requesters.get(pushMessage.getUserId());
            ByteBuf routeMetadata = TaggingMetadataCodec
                    .createTaggingContent(ByteBufAllocator.DEFAULT, Collections.singletonList(pushMessage.getRoute()));
            ByteBuf body = Unpooled.wrappedBuffer(pushMessage.getBody());
            Mono.just("服务端呼叫")
                    .flatMap(m -> requester.rsocketClient().requestResponse(
                                    Mono.just(
                                            ByteBufPayload.create(
                                                    body,
                                                    routeMetadata)))
                            .doOnSubscribe(subscription -> log.info("客户端订阅"))
                            .doOnError(throwable -> log.error("服务端呼叫失败 {}", throwable.toString()))
                            .doOnSuccess(p -> log.info("客户端返回: {}", p.getDataUtf8()))
                    )
                    .subscribe();
            return true;
        }
        return false;
    }
}
