package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ForwardMessage;

public interface ConsumeService {
    ForwardMessage consume(String consumer);
}
