package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.ForwardMessage;

public interface QuestService {
    void publish(ForwardMessage forwardMessage);

    void consume(ConsumeRecord consumeRecord);
}
