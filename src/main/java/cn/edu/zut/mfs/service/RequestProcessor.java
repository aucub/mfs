package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.LinkLog;
import cn.edu.zut.mfs.utils.RSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class RequestProcessor {

    public static NonBlockingHashMap<String, RSocketRequester> nonBlockingHashMap = new NonBlockingHashMap<>();
    private RedisService redisService;
    private LoginAuthService loginAuthService;

    @Autowired
    public void setLoginAuthService(LoginAuthService loginAuthService) {
        this.loginAuthService = loginAuthService;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void processRequests(RSocketRequester requester, String userId, String route) {
        Objects.requireNonNull(requester.rsocket())
                .onClose()
                .doFirst(() -> {
                    if (Objects.equals(route, "connect")) {
                        nonBlockingHashMap.put(userId, requester);
                    }
                    if (Boolean.TRUE.equals(redisService.hasKey("rsocket", userId))) {
                        redisService.incrementHash("rsocket", userId);
                    } else redisService.writeHash("rsocket", userId, 1);
                    loginAuthService.addLinkLog(new LinkLog(null, userId, new Date(), route, RSocketUtil.getRemoteAddressFromRequester(requester).toString()));
                    log.info("客户端: {} 连接", userId);
                })
                .doOnError(error -> log.warn("通道被客户端： {} 关闭", userId))
                .doFinally(consumer -> {
                    nonBlockingHashMap.remove(userId);
                    loginAuthService.addLinkLog(new LinkLog(null, userId, new Date(), "-" + route, RSocketUtil.getRemoteAddressFromRequester(requester).toString()));
                    if (redisService.loadHash("rsocket", userId) == 1) {
                        redisService.delete("rsocket", userId);
                    } else redisService.reduceHash("rsocket", userId);
                    log.info("客户端： {} 断开连接", userId);
                })
                .subscribe();
    }
}
