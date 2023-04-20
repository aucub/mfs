package cn.edu.zut.mfs.service;

import io.cloudevents.CloudEvent;

public interface PublishService {
    void publish(CloudEvent cloudEvent);
}
