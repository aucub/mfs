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

    public void processRequests(RSocketRequester requester, String userId,String route) {
        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", userId);
                    if(requesters.containsKey(userId)&&route=="connect") {
                        requesters.remove(userId);
                        requesters.put(userId, requester);
                    }
                    if(!requesters.containsKey(userId)) {
                        requesters.put(userId, requester);
                    }
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", userId))
                .doFinally(consumer -> {
                    requesters.remove(userId);
                    log.info("客户端： {} 断开连接", userId);
                })
                .subscribe();
    }
}
