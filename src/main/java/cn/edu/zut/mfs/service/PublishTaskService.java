package cn.edu.zut.mfs.service;

import io.cloudevents.CloudEvent;
import reactor.core.publisher.Flux;

public interface PublishTaskService {
    void publish(Flux<CloudEvent> cloudEventFlux);
}
