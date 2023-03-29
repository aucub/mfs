package cn.edu.zut.mfs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
public class RequestProcessor {
    public final static HashMap<String, RSocketRequester> requesters = new HashMap<>();

    public void processRequests(RSocketRequester requester, String client) {
        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", client);
                    requesters.put(client, requester);
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", client))
                .doFinally(consumer -> {
                    requesters.remove(client);
                    log.info("客户端： {} 断开连接", client);
                })
                .subscribe();
    }
}
