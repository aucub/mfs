package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.QuestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.questdb.client.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestServiceImpl implements QuestService {
    private static final ObjectMapper mapper = new ObjectMapper();


    public void publish(PublishRecord publishRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            try {
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
                        .stringColumn("body", mapper.writeValueAsString(publishRecord.getBody()))
                        .atNow();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

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
}
