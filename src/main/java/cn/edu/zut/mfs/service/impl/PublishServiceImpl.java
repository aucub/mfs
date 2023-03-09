package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.PublishService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {


    public RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setUsePublisherConnection(true);
    }

    @SneakyThrows
    public void publish(ForwardMessage forwardMessage) {
        Boolean result = rabbitTemplate.invoke(t -> {
            t.send(forwardMessage.getConsumer(), new Message(forwardMessage.getBody()));
            t.waitForConfirmsOrDie(10_000);
            return true;
        }, (tag, multiple) -> {
            log.info("Ack: " + tag + ":" + multiple);
        }, (tag, multiple) -> {
            log.info("Nack: " + tag + ":" + multiple);
        });
    }
}
