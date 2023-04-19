package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.QuestService;
import io.questdb.client.Sender;
import org.springframework.stereotype.Service;

@Service
public class QuestServiceImpl implements QuestService {


    public void publish(PublishRecord publishRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
                sender.table("PublishRecord")
                        .symbol("queueType", publishRecord.getQueueType())
                        .stringColumn("ID", publishRecord.getMessageId())
                        .longColumn("publishingId", publishRecord.getPublishingId())
                        .stringColumn("exchange", publishRecord.getExchange())
                        .stringColumn("appId", publishRecord.getAppId())
                        .stringColumn("userId", publishRecord.getUserId())
                        .stringColumn("routingKey", publishRecord.getRoutingKey())
                        .longColumn("priority", publishRecord.getPriority())
                        .stringColumn("correlationId", publishRecord.getCorrelationId())
                        .longColumn("offset", publishRecord.getOffset())
                        .stringColumn("expiration", publishRecord.getExpiration())
                        .longColumn("delay", publishRecord.getDelay())
                        .stringColumn("body", new String(publishRecord.getBody()))
                        .atNow();

        }
    }


    public void consume(ConsumeRecord consumeRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            sender.table("ConsumeRecord")
                    .symbol("queue", consumeRecord.getQueue())
                    .stringColumn("ID", consumeRecord.getMessageId())
                    .longColumn("publishingId", consumeRecord.getPublishingId())
                    .stringColumn("userId", consumeRecord.getUserId())
                    .atNow();
        }
    }

    public void push(PushMessage pushMessage) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            sender.table("Push")
                    .symbol("route", pushMessage.getRoute())
                    .stringColumn("userId", pushMessage.getUserId())
                    .stringColumn("body", new String(pushMessage.getBody()))
                        .atNow();
        }
    }
}
