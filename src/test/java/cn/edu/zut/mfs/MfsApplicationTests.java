package cn.edu.zut.mfs;

import cn.edu.zut.mfs.service.impl.RabbitMQServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MfsApplicationTests {

    @Autowired
    RabbitMQServiceImpl rabbitMQService;

    @Test
    void contextLoads() {
        try {
            rabbitMQService.test();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
