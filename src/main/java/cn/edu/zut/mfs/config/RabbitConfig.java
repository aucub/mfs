package cn.edu.zut.mfs.config;

import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;


@Configuration
public class RabbitConfig {

    @Bean
    RabbitStreamTemplate streamTemplate(Environment env) {
        env.streamCreator()
                .stream("mfs")
                .maxLengthBytes(ByteCapacity.GB(5))
                .maxSegmentSizeBytes(ByteCapacity.MB(100))
                .create();
        RabbitStreamTemplate template = new RabbitStreamTemplate(env, "mfs");
        template.setProducerCustomizer((name, builder) -> builder.name("mfs"));
        return template;
    }

    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> rabbitListenerContainerFactory(Environment env) {
        return new StreamRabbitListenerContainerFactory(env);
    }

    /*@Bean
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory() {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(Environment.builder()
                .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
                .build());
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> builder.name("Consumer")
                .offset(OffsetSpecification.first())
                .manualTrackingStrategy());
        return factory;
    }*/
}
