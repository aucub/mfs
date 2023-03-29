package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private static final String QUEUE_NAME="mfs1";
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

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }

    @Override
    //@RabbitListener(queues = "m")
    public void consumer(String in) {
        System.out.println(in);
    }
}
