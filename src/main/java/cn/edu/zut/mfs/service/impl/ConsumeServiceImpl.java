package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ForwardMessage consume(String consumer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue((rabbitTemplate.receive(consumer)).getBody(), ForwardMessage.class);
            //return new String(Objects.requireNonNull(rabbitTemplate.receive(consumer)).getBody(), "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //@RabbitListener(queues = "test")
    public void consumerBatch2(List<Message> messages) {

    }
}
