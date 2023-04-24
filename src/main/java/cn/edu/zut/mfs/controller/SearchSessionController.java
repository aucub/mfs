package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Sa-Token 会话查询
 */
@Slf4j
@RestController
@RequestMapping("/searchSession/")
public class SearchSessionController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("onlineList")
    public List<User> onlineList() {
        return userService.onlineList();
    }


    @PostMapping("onlineUsers")
    public long onlineUsers() {
        return userService.onlineUsers();
    }

}
