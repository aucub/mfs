package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishStreamService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishStreamServiceImpl implements PublishStreamService {
    private RabbitStreamTemplate rabbitStreamTemplate;

    @Autowired
    public void setRabbitStreamTemplate(RabbitStreamTemplate rabbitStreamTemplate) {
        this.rabbitStreamTemplate = rabbitStreamTemplate;
    }

    @Override
    public void publish(ForwardMessage forwardMessage) {
        rabbitStreamTemplate.send(new Message(forwardMessage.getBody()));
    }
}
