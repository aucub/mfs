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

import java.util.Objects;

@Service
public class PublishBatchServiceImpl implements PublishBatchService {
    private AmqpAdmin amqpAdmin;
    private BatchingRabbitTemplate batchingRabbitTemplate;
    private CachingConnectionFactory cachingConnectionFactory;

    public static void check(MetadataHeader metadataHeader, AmqpAdmin amqpAdmin) {
        if (metadataHeader.getExchange() == null || metadataHeader.getExchange().equals("")) {
            if (metadataHeader.getRoutingKey() != null) {
                if (amqpAdmin.getQueueInfo(metadataHeader.getRoutingKey()) == null)
                    throw new BaseException("队列不存在");
            } else throw new BaseException("未指定投递对象");
        }
    }

    @Autowired
    public void setCachingConnectionFactory(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    public void setup(int batchSize) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(50);
        scheduler.initialize();
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, Integer.MAX_VALUE, 30000);
        batchingRabbitTemplate = new BatchingRabbitTemplate(batchingStrategy, scheduler);
        batchingRabbitTemplate.setConnectionFactory(this.cachingConnectionFactory);
    }

    @Transactional
    public void sendMessage(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux) {
        check(metadataHeader, amqpAdmin);
        InfluxDBService influxDBService = new InfluxDBServiceImpl();
        cloudEventFlux.limitRate(100).subscribe(cloudEvent -> {
            Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
            batchingRabbitTemplate.send(metadataHeader.getExchange(), metadataHeader.getRoutingKey(), message);
            Thread.startVirtualThread(() -> {
                PublishRecord publishRecord = new PublishRecord(cloudEvent.getId(), cloudEvent.getSource().toString(), cloudEvent.getType(), (String) cloudEvent.getExtension("appid"), userId, Integer.parseInt((String) Objects.requireNonNull(Objects.requireNonNull(cloudEvent.getExtension("priority")))), (String) cloudEvent.getExtension("expiration"), Integer.parseInt((String) Objects.requireNonNull(cloudEvent.getExtension("delay"))), (String) cloudEvent.getExtension("dataContentType"), (String) cloudEvent.getExtension("contentEncoding"), cloudEvent.getSubject(), new String(Objects.requireNonNull(cloudEvent.getData()).toBytes()), Objects.requireNonNull(cloudEvent.getTime()).toInstant());
                influxDBService.publish(publishRecord);
            });
        });
    }


}
