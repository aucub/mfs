package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushRecord;

import java.time.Instant;
import java.util.List;

public interface InfluxDBService {
    void publish(PublishRecord publishRecord);

    void push(PushRecord pushRecord);

    void publishPoint(String messageId, Boolean result, Instant time);

    void consume(ConsumeRecord consumeRecord);

    List<PublishRecord> queryPublish(String start, String stop);

    List<ConsumeRecord> queryConsume(String start, String stop);

    List<PushRecord> queryPush(String start, String stop);

    void tranPublish(String start, String stop);

    void tranConsume(String start, String stop);

    void tranPush(String start, String stop);

    void close();
}
