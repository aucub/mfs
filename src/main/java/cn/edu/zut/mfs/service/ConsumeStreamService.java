package cn.edu.zut.mfs.service;

import com.rabbitmq.stream.MessageHandler;
import org.springframework.amqp.core.Message;

public interface ConsumeStreamService {
    void consumer(Message in, MessageHandler.Context context);
}
