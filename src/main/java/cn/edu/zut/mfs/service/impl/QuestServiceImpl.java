package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.QuestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.questdb.client.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestServiceImpl implements QuestService {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void publish(PublishRecord publishRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {

            sender.table("PublishRecord")
                    .symbol("exchange", publishRecord.getExchange())
                    .longColumn("ID", Long.parseLong(publishRecord.getMessageId()))
                    .stringColumn("userId", publishRecord.getUserId())
                    .stringColumn("routingKey", publishRecord.getRoutingKey())
                    .atNow();

        }
    }

    @Override
    public void consume(ConsumeRecord consumeRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            sender.table("ConsumeRecord")
                    .symbol("queue", consumeRecord.getQueue())
                    .longColumn("ID", Long.parseLong(consumeRecord.getMessageId()))
                    .stringColumn("userId", consumeRecord.getUserId())
                    .atNow();
        }
    }
}
