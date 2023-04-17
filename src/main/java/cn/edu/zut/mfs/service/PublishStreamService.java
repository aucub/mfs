package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.MetadataHeader;
import io.cloudevents.CloudEvent;
import reactor.core.publisher.Flux;

public interface PublishStreamService {
    void publish(Flux<CloudEvent> cloudEventFlux, MetadataHeader metadataHeader);
}
