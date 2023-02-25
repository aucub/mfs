package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.model.Message;

public interface RabbitMQService {
    void sender() throws InterruptedException;

    void receiver(Message message);
}
