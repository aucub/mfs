package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConfirmCallbackService;
import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.service.ReturnCallbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private final static ObjectMapper mapper = new ObjectMapper();
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    private ConfirmCallbackService confirmCallbackService;
    private ReturnCallbackService returnCallbackService;

    @Autowired
    public void setConfirmCallbackService(ConfirmCallbackService confirmCallbackService) {
        this.confirmCallbackService = confirmCallbackService;
    }

    @Autowired
    public void setReturnCallbackService(ReturnCallbackService returnCallbackService) {
        this.returnCallbackService = returnCallbackService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(confirmCallbackService);
        rabbitTemplate.setReturnsCallback(returnCallbackService);
        rabbitTemplate.setUsePublisherConnection(true);
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(ForwardMessage forwardMessage) {
       // amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), true, false, true));
        rabbitTemplate.send(forwardMessage.getQueue(), new Message(mapper.writeValueAsBytes(forwardMessage)));
        /*rabbitTemplate.send(forwardMessage.getExchange(), forwardMessage.getQueue(), new Message(mapper.writeValueAsBytes(forwardMessage)),
                new CorrelationData(UUID.randomUUID().toString()));*/
    }

    @SneakyThrows
    public void publish1(ForwardMessage forwardMessage) {
        amqpAdmin.declareQueue(new Queue(forwardMessage.getQueue(), true, false, true));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(15000);
        rabbitTemplate.send(forwardMessage.getQueue(), new Message(mapper.writeValueAsBytes(forwardMessage)));
        rabbitTemplate.send(forwardMessage.getExchange(), forwardMessage.getQueue(), new Message(mapper.writeValueAsBytes(forwardMessage)),
                new CorrelationData(UUID.randomUUID().toString()));
    }

}
