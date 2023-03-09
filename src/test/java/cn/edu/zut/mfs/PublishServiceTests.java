package cn.edu.zut.mfs;

import cn.edu.zut.mfs.domain.Message;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.impl.PublishServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PublishServiceTests {
    Message message = new Message("test", "test4", "test".getBytes());
    @Autowired
    PublishServiceImpl publishService;
    @Autowired
    ConsumeService consumeService;

    @Test
    public void test1() {
        publishService.publish(message);
    }

    @Test
    public void test2() {
        System.out.println(consumeService.consume(message.getConsumer()));
    }
}
