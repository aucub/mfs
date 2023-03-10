package cn.edu.zut.mfs;

import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeService;
import cn.edu.zut.mfs.service.impl.PublishServiceImpl;
import cn.edu.zut.mfs.service.impl.PublishStreamServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
public class RabbitMQServiceTests {
    ForwardMessage forwardMessage = new ForwardMessage("test", "test4", "test".getBytes());
    @Autowired
    PublishServiceImpl publishService;
    @Autowired
    ConsumeService consumeService;
    @Autowired
    PublishStreamServiceImpl publishStreamService;

    @Test
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
    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    public void test4() {
        publishStreamService.consume();
    }

    @Test
    public void test5() {
        publishStreamService.publish(forwardMessage);
    }

    @Test
    public void test7() throws Exception {
        PublishStreamServiceImpl listener = this.harness.getSpy("mfs");
        assertNotNull(listener);
        LatchCountDownAndCallRealMethodAnswer answer = this.harness.getLatchAnswerFor("mfs", 1);
        doAnswer(answer).when(listener).nativeMsg(anyString());
        assertTrue(answer.await(10));
    }


}
