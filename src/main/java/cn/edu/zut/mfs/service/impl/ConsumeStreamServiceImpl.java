package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.service.RSocketServer;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {
    private final static Environment environment = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    QuestService questService;
    RSocketServer rSocketServer;
    private Consumer consumer;

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    @Autowired
    public void setSocketServer(RSocketServer rSocketServer) {
        this.rSocketServer = rSocketServer;
    }

    @Override
    public Flux<byte[]> consume(Consume consume) {
        Flux<byte[]> f = Flux.create(emitter -> {
            consumer =
                    environment.consumerBuilder()
                            .stream(consume.getQueue())
                            .name(consume.getUserId())
                            .offset(OffsetSpecification.first())
                            .autoTrackingStrategy()
                            .builder()
                            .messageHandler((context, message) -> emitter.next(message.getBodyAsBinary())
                            )
                            .build();
            emitter.onDispose(() -> {
                consumer.close();
            });
        });
        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> "暂无新消息".getBytes())
                .mergeWith(f);
    }
}
