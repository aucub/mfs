package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Consume;
import io.cloudevents.core.v1.CloudEventV1;
import reactor.core.publisher.Flux;

public interface ConsumeBatchService {
    Flux<CloudEventV1> consume(String userId, Consume consume);
}
