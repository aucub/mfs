package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private final static ObjectMapper mapper = new ObjectMapper();
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ForwardMessage consume(Consume consume) {
        try {
            return mapper.readValue((Objects.requireNonNull(rabbitTemplate.receive(consume.getQueue()))).getBody(), ForwardMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
