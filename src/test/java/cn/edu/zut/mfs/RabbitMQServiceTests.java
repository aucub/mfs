package cn.edu.zut.mfs;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.impl.PublishServiceImpl;
import cn.edu.zut.mfs.service.impl.PublishStreamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RabbitMQServiceTests {
    ForwardMessage forwardMessage = new ForwardMessage("test", "test4", "test".getBytes());
    @Autowired
    PublishServiceImpl publishService;
    @Autowired
    ConsumeService consumeService;
    @Autowired
    PublishStreamServiceImpl publishStreamService;

    /*@Test
    public void test1() {
        publishService.publish(forwardMessage);
    }

    @Test
    public void test2() {
        System.out.println(consumeService.consume(forwardMessage.getConsumer()));
    }

    @Test
    public void test3() {
        publishStreamService.publish(forwardMessage);
    }

    @Test
    public void test5() {
        publishStreamService.publish(forwardMessage);
    }*/


}
