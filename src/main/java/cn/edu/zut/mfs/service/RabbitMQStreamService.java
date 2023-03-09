package cn.edu.zut.mfs.service;

import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.rabbit.stream.retry.StreamRetryOperationsInterceptorFactoryBean;
import org.springframework.retry.support.RetryTemplate;

public class RabbitMQStreamService {
    @Bean
    RabbitStreamTemplate streamTemplate(Environment env) {
        RabbitStreamTemplate template = new RabbitStreamTemplate((com.rabbitmq.stream.Environment) env, "mfs");
        template.setProducerCustomizer((name, builder) -> builder.name("test"));
        return template;
    }

    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> rabbitListenerContainerFactory(Environment env) {
        return new StreamRabbitListenerContainerFactory((com.rabbitmq.stream.Environment) env);
    }


    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory(Environment env) {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory((com.rabbitmq.stream.Environment) env);
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> builder.name("myConsumer")
                .offset(OffsetSpecification.first())
                .manualTrackingStrategy());
        return factory;
    }

    @Bean
    public StreamRetryOperationsInterceptorFactoryBean sfb(RetryTemplate retryTemplate) {
        StreamRetryOperationsInterceptorFactoryBean rfb =
                new StreamRetryOperationsInterceptorFactoryBean();
        rfb.setRetryOperations(retryTemplate);
        rfb.setStreamMessageRecoverer((msg, context, throwable) -> {
        });
        return rfb;
    }

}
