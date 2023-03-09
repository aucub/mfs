package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.domain.Message;

public interface PublishService {
    void publish(Message message);
}
