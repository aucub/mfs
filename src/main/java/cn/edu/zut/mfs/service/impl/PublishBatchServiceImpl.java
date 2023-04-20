package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.InfluxDBService;
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
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void sendMessage(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        cloudEventFlux.limitRate(10).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            batchingRabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Integer.valueOf((String) cloudEvent.getExtension("priority")), (String) cloudEvent.getExtension("expiration"), Integer.valueOf((String) cloudEvent.getExtension("delay")), (String) cloudEvent.getExtension("dataContentType"), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(cloudEvent.getData().toBytes()), cloudEvent.getTime().toInstant());
            influxDBService.publish(publishRecord);
        });
    }


}
