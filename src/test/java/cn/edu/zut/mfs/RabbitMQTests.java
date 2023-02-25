package cn.edu.zut.mfs;

import cn.edu.zut.mfs.model.Message;
import cn.edu.zut.mfs.service.impl.RabbitMQServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQTests {
    @Autowired
    RabbitMQServiceImpl rabbitMQService;

    Message message = new Message(5, "reactor.rabbitmq.spring.boot");

    @Test
    public void SendTest() {
        try {
            rabbitMQService.sender();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Receiver() {
        rabbitMQService.receiver(message);
    }
}
