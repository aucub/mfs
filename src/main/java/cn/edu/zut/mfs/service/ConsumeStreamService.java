package cn.edu.zut.mfs.service;

import com.rabbitmq.stream.MessageHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface ConsumeStreamService {
    @RabbitListener(queues = "mfs")//, containerFactory = "nativeFactory"
    void consumer(String in);
}
