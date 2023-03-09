package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.ConsumeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConsumeServiceImpl implements ConsumeService {
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String consume(String consumer) {
        return Objects.requireNonNull(rabbitTemplate.receive(consumer)).toString();
    }
}
