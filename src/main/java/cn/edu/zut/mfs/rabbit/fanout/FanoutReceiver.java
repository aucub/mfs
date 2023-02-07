package cn.edu.zut.mfs.rabbit.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout")
public class FanoutReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver  : " + message);
    }

}
