package cn.edu.zut.mfs.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * RabbitMQ配置
 */
public class RabbitConfig {
    @Bean
    TaskExecutor exec() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(10);
        return exec;
    }

    @Bean
    CachingConnectionFactory ccf() {
        CachingConnectionFactory ccf = new CachingConnectionFactory("localhost", 5672);
        ccf.setVirtualHost("mfs");
        CachingConnectionFactory publisherCF = (CachingConnectionFactory) ccf.getPublisherConnectionFactory();
        if (publisherCF != null) {
            publisherCF.setChannelCacheSize(1);
            publisherCF.setChannelCheckoutTimeout(1000L);
        }
        return ccf;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(new FatalExceptionStrategy()));
        return factory;
    }

    public static class FatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

        @Override
        public boolean isFatal(@NotNull Throwable t) {
            return super.isFatal(t);
        }

    }


}
