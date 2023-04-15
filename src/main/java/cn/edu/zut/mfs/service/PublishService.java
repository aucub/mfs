package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.MetadataHeader;
import io.cloudevents.CloudEvent;

public interface PublishService {
    void publish(CloudEvent cloudEvent, MetadataHeader metadataHeader);
}
