package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ForwardMessage;

public interface RabbitMQService {
    void sender(ForwardMessage forwardMessage) throws InterruptedException;

    void receiver(ForwardMessage forwardMessage) throws InterruptedException;
}
