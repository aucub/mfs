package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.domain.PushMessage;

public interface RSocketServer {
    void push(PushMessage pushMessage);
}
