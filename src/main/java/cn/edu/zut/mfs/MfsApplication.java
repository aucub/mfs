package cn.edu.zut.mfs;

import com.dtp.core.spring.EnableDynamicTp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableDynamicTp
@EnableRabbit
@SpringBootApplication
public class MfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MfsApplication.class, args);
    }

}
