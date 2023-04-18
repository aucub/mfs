package cn.edu.zut.mfs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class RequestProcessor {

    private RedisService redisService;

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void processRequests(RSocketRequester requester, String userId, String route) {
        atomicInteger.getAndIncrement();
        if (atomicInteger.get() % 1000 == 0) {
            System.out.println(atomicInteger.get());
            log.error(String.valueOf(atomicInteger.get()));
        }
        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    log.info("客户端: {} 连接", userId);
                    if (redisService.hasKey("rsocket", userId) && Objects.equals(route, "connect")) {
                        redisService.delete("rsocket", userId);
                        redisService.writeHash("rsocket", userId, requester);
                    }
                    if (redisService.hasKey("rsocket", userId)) {
                        redisService.writeHash("rsocket", userId, requester);
                    }
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", userId))
                .doFinally(consumer -> {
                    redisService.delete("rsocket", userId);
                    log.info("客户端： {} 断开连接", userId);
                })
                .subscribe();
    }
}
