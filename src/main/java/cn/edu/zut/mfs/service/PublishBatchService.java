package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.MetadataHeader;
import io.cloudevents.CloudEvent;
import reactor.core.publisher.Flux;

public interface PublishBatchService {
    void setup(int batchSize);

    void sendMessage(String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux);
}
