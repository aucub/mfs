package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.RSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PushController {
    private RSocketServer rSocketServer;

    @Autowired
    public void setrSocketServer(RSocketServer rSocketServer) {
        this.rSocketServer = rSocketServer;
    }

    @PreAuthorize("hasRole('push')")
    @PostMapping("/push")
    public Boolean push(@RequestBody PushMessage pushMessage) {
        return rSocketServer.push(pushMessage);
    }
}
