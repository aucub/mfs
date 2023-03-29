package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private RabbitTemplate rabbitTemplate;
    private final static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ForwardMessage consume(String queue) {
        try {
            return mapper.readValue((rabbitTemplate.receive(queue)).getBody(), ForwardMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
