package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


/**
 * 消费控制器
 */
@Slf4j
@RestController
public class ConsumeController {
    private ConsumeStreamService consumeStreamService;

    private ConsumeService consumeService;


    @Autowired
    public void setConsumeService(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }

    @Autowired
    public void setConsumeStreamService(ConsumeStreamService consumeStreamService) {
        this.consumeStreamService = consumeStreamService;
    }

    @MessageMapping("consume")
    public Flux<byte[]> consume(Consume consume) {
        if (consume.getQueueType().equals("stream")) {
            return consumeStreamService.consume(consume);
        }
        return consumeService.consume(consume);
    }

}

