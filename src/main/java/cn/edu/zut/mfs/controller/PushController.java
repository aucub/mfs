package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.service.RSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PushController {
    private RSocketServer rSocketServer;
    private QuestService questService;

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    @Autowired
    public void setrSocketServer(RSocketServer rSocketServer) {
        this.rSocketServer = rSocketServer;
    }

    @PostMapping("/push")
    public Boolean push(@RequestBody PushMessage pushMessage) {
        questService.push(pushMessage);
        return rSocketServer.push(pushMessage);
    }
}
