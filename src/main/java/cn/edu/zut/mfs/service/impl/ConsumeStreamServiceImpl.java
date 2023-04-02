package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.service.RSocketServer;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {
    QuestService questService;

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    private final static Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    private Consumer consumer;

    RSocketServer rSocketServer;

    @Autowired
    public void setrSocketServer(RSocketServer rSocketServer) {
        this.rSocketServer = rSocketServer;
    }

    @Override
    public void consume(Consume consume) {
        consumer =
                environment.consumerBuilder()
                        .stream(consume.getQueue())
                        .name(consume.getClient())
                        .offset(OffsetSpecification.first())
                        .autoTrackingStrategy()
                        .builder()
                        .messageHandler((context, message) -> Thread.startVirtualThread(() -> {
                            System.out.println(message.getBody());
                            questService.consume(new ConsumeRecord("78904",consume.getQueue(), consume.getClient()));
                        }))
                        .build();
        consumer.storedOffset();
        try {
            wait(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
