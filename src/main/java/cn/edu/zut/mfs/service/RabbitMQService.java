package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.rabbitmq.client.Delivery;
import reactor.core.publisher.Flux;

public interface RabbitMQService {
    void sender(ForwardMessage forwardMessage) throws InterruptedException;

    void createFanout(ForwardMessage forwardMessage) throws InterruptedException;

    void createTopic(ForwardMessage forwardMessage) throws InterruptedException;

    Flux<Delivery> receiver(ForwardMessage forwardMessage) throws InterruptedException;
}
