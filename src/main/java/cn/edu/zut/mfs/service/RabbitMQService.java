package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.model.Message;

public interface RabbitMQService {
    void Sender(Message message);

    void Receiver(String QUEUE, int count);
}
