package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.domain.Event;
import cn.edu.zut.mfs.domain.ForwardMessage;

public interface PublishService {
    void publish(ForwardMessage forwardMessage);
}
