package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PublishBatchServiceImpl {
    private BatchingRabbitTemplate batchingRabbitTemplate;
    private BatchingStrategy batchingStrategy;

    private ThreadPoolTaskScheduler scheduler;


    private CachingConnectionFactory cachingConnectionFactory;

    public void setup(int batchSize) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.initialize();
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, Integer.MAX_VALUE, 30000);
        batchingRabbitTemplate = new BatchingRabbitTemplate(batchingStrategy, this.scheduler);
        batchingRabbitTemplate.setConnectionFactory(this.cachingConnectionFactory);
    }

    private void sendMessage(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader) {
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            batchingRabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), MessageConverter.toMessage((CloudEventV1) cloudEvent));
        });
    }


}
