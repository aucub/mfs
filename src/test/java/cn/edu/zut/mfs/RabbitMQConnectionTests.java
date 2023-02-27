package cn.edu.zut.mfs;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.impl.RabbitMQServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQConnectionTests {
    @Autowired
    RabbitMQServiceImpl rabbitMQService;

    ForwardMessage forwardMessage = new ForwardMessage();

    @Test
    public void SendTest() {
        forwardMessage.setQUEUE("reactor.rabbitmq.spring.boot");
        try {
            rabbitMQService.sender(forwardMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Receiver() {
        forwardMessage.setQUEUE("reactor.rabbitmq.spring.boot");
        try {
            rabbitMQService.receiver(forwardMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
