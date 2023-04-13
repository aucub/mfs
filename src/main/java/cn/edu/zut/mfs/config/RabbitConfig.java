package cn.edu.zut.mfs.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class RabbitConfig {
    @Bean
    TaskExecutor exec() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(10);
        return exec;
    }

    @Bean
    CachingConnectionFactory ccf() {
        CachingConnectionFactory ccf = new CachingConnectionFactory("localhost");
        CachingConnectionFactory publisherCF = (CachingConnectionFactory) ccf.getPublisherConnectionFactory();
        publisherCF.setChannelCacheSize(1);
        publisherCF.setChannelCheckoutTimeout(1000L);
        return ccf;
    }
}
