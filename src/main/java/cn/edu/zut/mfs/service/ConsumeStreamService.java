package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.rabbitmq.stream.MessageHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface ConsumeStreamService {
    void consumer(String in);
}
