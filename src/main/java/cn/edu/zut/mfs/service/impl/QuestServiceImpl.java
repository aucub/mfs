package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.ForwardMessage;
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

    @Override
    public void publish(ForwardMessage forwardMessage) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            try {
                sender.table("ForwardMessage")
                        .symbol("exchange", forwardMessage.getExchange())
                        //.longColumn("ID", Long.parseLong(forwardMessage.getId()))
                        //.stringColumn("client", forwardMessage.getClient())
                        .stringColumn("queue", forwardMessage.getQueue())
                        .stringColumn("message", mapper.writeValueAsString(forwardMessage))
                        .atNow();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void consume(ConsumeRecord consumeRecord) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            sender.table("ConsumeRecord")
                    .symbol("queue", consumeRecord.getQueue())
                    .longColumn("ID", Long.parseLong(consumeRecord.getId()))
                    .stringColumn("client", consumeRecord.getClient())
                    .atNow();
        }
    }
}
