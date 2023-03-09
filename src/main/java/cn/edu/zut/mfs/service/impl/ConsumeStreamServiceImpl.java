package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.ConsumeStreamService;
import com.rabbitmq.stream.MessageHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;


@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {
    private RabbitStreamTemplate rabbitStreamTemplate;

    @Autowired
    public void setRabbitStreamTemplate(RabbitStreamTemplate rabbitStreamTemplate) {
        this.rabbitStreamTemplate = rabbitStreamTemplate;
    }

    @Override
    @RabbitListener(queues = "mfs", containerFactory = "nativeFactory")
    public void consumer(Message in, MessageHandler.Context context) {
        in.getBody();
        context.storeOffset();
    }
}
