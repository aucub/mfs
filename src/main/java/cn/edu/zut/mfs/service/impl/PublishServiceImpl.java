package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Event;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {


    private RabbitTemplate rabbitTemplate;
    AmqpAdmin amqpAdmin;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setUsePublisherConnection(true);
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(ForwardMessage forwardMessage) {
        ObjectMapper mapper = new ObjectMapper();
        amqpAdmin.declareQueue(new Queue(forwardMessage.getConsumer(), true, false, true));
        rabbitTemplate.send(forwardMessage.getConsumer(), new Message(mapper.writeValueAsBytes(forwardMessage)));
        System.out.println(forwardMessage.toString());
        //t.send(forwardMessage.getConsumer(), new Message(mapper.writeValueAsBytes(forwardMessage)));
    }

    @Override
    @SneakyThrows
    public void publish(Event event) {
        ObjectMapper mapper = new ObjectMapper();
        amqpAdmin.declareQueue(new Queue(event.getTopic(), true, false, true));
        rabbitTemplate.send(event.getTopic(), new Message(mapper.writeValueAsBytes(event)));
        System.out.println(event.toString());
    }
}
