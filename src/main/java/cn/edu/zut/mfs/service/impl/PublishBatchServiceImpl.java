package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.MetadataHeader;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.PublishBatchService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.AmqpAdmin;
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
    private static AmqpAdmin amqpAdmin;
    private BatchingRabbitTemplate batchingRabbitTemplate;
    private ThreadPoolTaskScheduler scheduler;
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    public static void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        PublishBatchServiceImpl.amqpAdmin = amqpAdmin;
    }

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
        if (metadataHeader.getExchange() == null || metadataHeader.getExchange() == "") {
            if (metadataHeader.getRoutingKey() != null) {
                if (amqpAdmin.getQueueInfo(metadataHeader.getRoutingKey()) == null)
                    throw new BaseException("队列不存在");
            } else throw new BaseException("未指定投递对象");
        }
        cloudEventFlux.limitRate(100).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            batchingRabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            Thread.startVirtualThread(() -> {
                PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource().toString(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Integer.valueOf((String) cloudEvent.getExtension("priority")), (String) cloudEvent.getExtension("expiration"), Integer.valueOf((String) cloudEvent.getExtension("delay")), (String) cloudEvent.getExtension("dataContentType"), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(cloudEvent.getData().toBytes()), cloudEvent.getTime().toInstant());
                influxDBService.publish(publishRecord);
            });
        });
    }


}
