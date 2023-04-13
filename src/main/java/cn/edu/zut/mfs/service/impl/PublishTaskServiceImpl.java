package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ForwardMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.TaskExecutor;

public class PublishTaskServiceImpl {
    private final static ObjectMapper mapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;

    private final TaskExecutor exec;

    public PublishTaskServiceImpl(RabbitTemplate rabbitTemplate, TaskExecutor exec) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setUsePublisherConnection(true);
        this.exec = exec;
    }

    @SneakyThrows
    public void publish(ForwardMessage forwardMessage) {
        rabbitTemplate.send(forwardMessage.getQueue(), new Message(mapper.writeValueAsBytes(forwardMessage)));
    }
}
