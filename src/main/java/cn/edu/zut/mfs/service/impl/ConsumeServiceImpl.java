package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.ConsumeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
public class ConsumeServiceImpl implements ConsumeService {
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String consume(String consumer) {
        try {
            return new String(Objects.requireNonNull(rabbitTemplate.receive(consumer)).getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}
