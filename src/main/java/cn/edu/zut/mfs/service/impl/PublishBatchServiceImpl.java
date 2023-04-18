package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.PublishBatchService;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PublishBatchServiceImpl implements PublishBatchService {
    private BatchingRabbitTemplate batchingRabbitTemplate;

    private ThreadPoolTaskScheduler scheduler;


    private CachingConnectionFactory cachingConnectionFactory;
    private QuestService questService;

    @Autowired
    public void setCachingConnectionFactory(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    public void setup(int batchSize) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.initialize();
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, Integer.MAX_VALUE, 30000);
        batchingRabbitTemplate = new BatchingRabbitTemplate(batchingStrategy, this.scheduler);
        batchingRabbitTemplate.setConnectionFactory(this.cachingConnectionFactory);
    }

    public void sendMessage(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            batchingRabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            questService.publish(new PublishRecord(cloudEvent.getId(), message.getMessageProperties().getAppId(), message.getMessageProperties().getUserId(), message.getMessageProperties().getPriority(), message.getMessageProperties().getCorrelationId(), message.getMessageProperties().getExpiration(), metadataHeader.getExchange(), message.getMessageProperties().getDelay(), 0, metadataHeader.getRoutingKey(), "classic", 0, cloudEvent.getData().toBytes()));
        });
    }


}
