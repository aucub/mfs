package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.PublishBatchService;
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

    @Autowired
    public void setCachingConnectionFactory(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    public void setup(int batchSize) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(50);
        scheduler.initialize();
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, Integer.MAX_VALUE, 30000);
        batchingRabbitTemplate = new BatchingRabbitTemplate(batchingStrategy, this.scheduler);
        batchingRabbitTemplate.setConnectionFactory(this.cachingConnectionFactory);
    }

    public void sendMessage(Flux<CloudEvent> cloudEventFlux) {
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            batchingRabbitTemplate.send((String) cloudEvent.getExtension("exchange"), (String) cloudEvent.getExtension("routekey"), message);
        });
    }


}
