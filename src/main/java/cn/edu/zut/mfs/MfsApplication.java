package cn.edu.zut.mfs;

import cn.edu.zut.mfs.service.QUICHTTP3Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MfsApplication.class, args);
        QUICHTTP3Server.initServer();
        QUICHTTP3Server.Server();
    }

}
