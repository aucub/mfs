package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.service.ConsumeBatchService;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import cn.edu.zut.mfs.service.RequestProcessor;
import cn.edu.zut.mfs.utils.JwtUtils;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private ConsumeBatchService consumeBatchService;

    private RequestProcessor requestProcessor;

    @Autowired
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Autowired
    public void setConsumeService(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }

    @Autowired
    public void setConsumeStreamService(ConsumeStreamService consumeStreamService) {
        this.consumeStreamService = consumeStreamService;
    }

    @Autowired
    public void setConsumeBatchService(ConsumeBatchService consumeBatchService) {
        this.consumeBatchService = consumeBatchService;
    }

    @MessageMapping("consume")
    public Flux<CloudEventV1> consume(RSocketRequester requester, @AuthenticationPrincipal String token, Consume consume) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "consume");
        if (consume.getQueueType().equals("stream")) {
            return consumeStreamService.consume(consume);
        }
        return consumeService.consume(consume);
    }

    @MessageMapping("consumeBatch")
    public Flux<CloudEventV1> ConsumeBatch(RSocketRequester requester, @AuthenticationPrincipal String token, Consume consume) {
        requestProcessor.processRequests(requester, JwtUtils.decode(token).getSubject(), "consumeBatch");
        return consumeBatchService.consume(consume);
    }

}

