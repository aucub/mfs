package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("onlineList")
    @PreAuthorize("hasRole('searchOnline')")
    public Mono<List<User>> onlineList() {
        return Mono.just(userService.onlineList());
    }


    @GetMapping("onlineUsers")
    @PreAuthorize("hasRole('searchOnline')")
    public Mono<Long> onlineUsers() {
        return Mono.just(userService.onlineUsers());
    }

}
