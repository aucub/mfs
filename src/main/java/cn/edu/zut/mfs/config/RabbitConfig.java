package cn.edu.zut.mfs.config;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;


@Configuration
public class RabbitConfig {
    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory() {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(Environment.builder()
                .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
                .build());
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> builder.name("Consumer")
                .offset(OffsetSpecification.first())
                .manualTrackingStrategy());
        return factory;
    }
}
