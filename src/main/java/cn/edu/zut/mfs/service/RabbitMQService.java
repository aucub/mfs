package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Message;
import com.rabbitmq.client.Delivery;
import reactor.core.publisher.Flux;

public interface RabbitMQService {
    void sender(Message message) throws InterruptedException;

    void createFanout(Message message) throws InterruptedException;

    void createTopic(Message message) throws InterruptedException;

    Flux<Delivery> receiver(Message message) throws InterruptedException;
}
