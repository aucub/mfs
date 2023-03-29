package cn.edu.zut.mfs;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MessageListenerFor implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("44444444");
    }
}
