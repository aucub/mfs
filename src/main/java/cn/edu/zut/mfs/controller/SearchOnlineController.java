package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/searchOnline/")
public class SearchOnlineController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 在线列表
     *
     * @return {@link Mono}<{@link List}<{@link User}>>
     */
    @GetMapping("onlineList")
    @PreAuthorize("hasRole('searchOnline')")
    @MessageMapping("/onlineList")
    public Mono<List<User>> onlineList() {
        return Mono.just(userService.onlineList());
    }


    /**
     * 在线用户
     *
     * @return {@link Mono}<{@link Long}>
     */
    @GetMapping("onlineUsers")
    @MessageMapping("/onlineUsers")
    @PreAuthorize("hasRole('searchOnline')")
    public Mono<Long> onlineUsers() {
        return Mono.just(userService.onlineUsers());
    }

    @GetMapping("clearConnectCache")
    @MessageMapping("/clearConnectCache")
    @PreAuthorize("hasRole('searchOnline')")
    @Scheduled(fixedDelay = 1800000)
    @CacheEvict(value = "connectList", allEntries = true)
    public Mono<Void> clearConnectCache() {
        return Mono.empty();
    }

    @GetMapping("clearOnLineCache")
    @MessageMapping("/clearOnLineCache")
    @PreAuthorize("hasRole('searchOnline')")
    @Scheduled(fixedDelay = 1800000)
    @CacheEvict(value = "onlineList", allEntries = true)
    public Mono<Void> clearOnLineCache() {
        return Mono.empty();
    }

}
