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
    public BaseResponse<ForwardMessage> consume(Consume consume) {
        return BaseResponse.success(consumeService.consume(consume));
    }

    @MessageMapping("consumeStream")
    public void consumeStream(Consume consume) {
        consumeStreamService.consume(consume);
    }

}

