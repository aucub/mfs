package cn.edu.zut.mfs.rabbit.object;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        System.out.println("Sender object: ");
        this.rabbitTemplate.convertAndSend("object");
    }

}
