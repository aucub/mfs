package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.QuestService;
import io.questdb.client.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestServiceImpl implements QuestService {
    @Override
    public void publish(ForwardMessage forwardMessage) {
        try (Sender sender = Sender.builder().address("47.113.201.150:9009").build()) {
            sender.table("ForwardMessage")
                    .symbol("topic", forwardMessage.getTopic())
                    .longColumn("ID", Long.parseLong(forwardMessage.getId()))
                    .stringColumn("client", forwardMessage.getClient())
                    .stringColumn("body", forwardMessage.toString())
                    .atNow();
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
