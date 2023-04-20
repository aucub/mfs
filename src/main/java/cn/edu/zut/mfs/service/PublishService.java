package cn.edu.zut.mfs.service;

import io.cloudevents.CloudEvent;
import reactor.core.publisher.Flux;

public interface PublishService {
    void publish(Flux<CloudEvent> cloudEventFlux);
}
