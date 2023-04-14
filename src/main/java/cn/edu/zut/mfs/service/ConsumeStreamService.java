package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Consume;
import reactor.core.publisher.Flux;

public interface ConsumeStreamService {
    Flux<byte[]> consume(Consume consume);
}
