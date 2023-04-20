package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.PublishService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    private static AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    @SneakyThrows
    public void publish(CloudEvent cloudEvent) {
        Message message = MessageConverter.toMessage((CloudEventV1) cloudEvent);
        rabbitTemplate.send((String) cloudEvent.getExtension("exchange"), (String) cloudEvent.getExtension("routekey"), message);
    }

}
