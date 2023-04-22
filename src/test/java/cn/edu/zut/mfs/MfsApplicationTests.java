package cn.edu.zut.mfs;

import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.impl.InfluxDBServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MfsApplicationTests {

    @Test
    void contextLoads() {
    }

    InfluxDBService influxDBService = new InfluxDBServiceImpl();

    @Test
    void Test1() {
        influxDBService.queryPublish();
    }

}
