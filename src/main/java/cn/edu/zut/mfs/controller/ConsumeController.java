package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Slf4j
@RestController
@MessageMapping("consume")
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
        return consumeService.consume(consume);
    }

    @MessageMapping("consumeStream")
    public Flux<byte[]> consumeStream(Consume consume) {
        return consumeStreamService.consume(consume);
    }

}

