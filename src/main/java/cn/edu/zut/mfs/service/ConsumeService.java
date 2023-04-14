package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Consume;
import reactor.core.publisher.Flux;

public interface ConsumeService {
    Flux<byte[]> consume(Consume consume);
}
