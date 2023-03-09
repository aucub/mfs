package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Message;
import cn.edu.zut.mfs.service.PublishService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishServiceImpl implements PublishService {

    public RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Message message) {
        rabbitTemplate.convertAndSend("", message.getConsumer(), message.getBody());
    }
}
