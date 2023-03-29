package cn.edu.zut.mfs.service;

public interface ConsumeStreamService {
    void consume(String stream,String client);
}
