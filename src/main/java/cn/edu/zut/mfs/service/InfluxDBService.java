package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;

public interface InfluxDBService {
    void publish(PublishRecord publishRecord);

    void consume(ConsumeRecord consumeRecord);

    void close();
}
