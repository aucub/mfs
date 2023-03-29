package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.domain.ForwardMessage;

public interface RSocketServer {
    void send(ForwardMessage forwardMessage);
}
