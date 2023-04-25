package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;

import java.time.Instant;
import java.util.List;

public interface InfluxDBService {
    void publish(PublishRecord publishRecord);

    void push(PushMessage pushMessage);

    void publishPoint(String messageId, Boolean result, Instant time);

    void consume(ConsumeRecord consumeRecord);

    List<PublishRecord> queryPublish(String start, String stop);

    List<ConsumeRecord> queryConsume(String start, String stop);

    List<PushMessage> queryPush(String start, String stop);

    void tranPublish(String start, String stop);

    void tranConsume(String start, String stop);

    void tranPush(String start, String stop);

    void close();
}
