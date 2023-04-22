package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.MetadataHeader;
import io.cloudevents.CloudEvent;
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;
import reactor.core.publisher.Flux;


public interface PublishStreamService {
    void publish(MpmcAtomicArrayQueue mpmcAtomicArrayQueue, String userId, MetadataHeader metadataHeader, Flux<CloudEvent> cloudEventFlux);
}
