package cn.edu.zut.mfs.config;

import cn.edu.zut.mfs.service.impl.PublishStreamServiceImpl;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@RabbitListenerTest
@Configuration
public class RabbitConfig {
    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory(Environment env) {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(env);
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> {
            builder.name("myConsumer")
                    .offset(OffsetSpecification.first())
                    .manualTrackingStrategy();
        });
        return factory;
    }

    @Bean
    public PublishStreamServiceImpl publishStreamService() {
        return new PublishStreamServiceImpl();
    }
}
