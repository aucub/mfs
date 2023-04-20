package cn.edu.zut.mfs.service;

import io.cloudevents.CloudEvent;

public interface PublishStreamService {
    void publish(CloudEvent cloudEvent);
}
