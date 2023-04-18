package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;

public interface QuestService {
    void publish(PublishRecord publishRecord);

    void consume(ConsumeRecord consumeRecord);

    void push(PushMessage pushMessage);
}
