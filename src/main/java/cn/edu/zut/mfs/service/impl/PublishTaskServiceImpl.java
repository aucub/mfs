package cn.edu.zut.mfs.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.TaskExecutor;

public class PublishTaskServiceImpl {
    private final RabbitTemplate rabbitTemplate;

    private final TaskExecutor exec;

    public PublishTaskServiceImpl(RabbitTemplate rabbitTemplate, TaskExecutor exec) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setUsePublisherConnection(true);
        this.exec = exec;
    }
}
