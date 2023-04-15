package cn.edu.zut.mfs.config;

import io.cloudevents.spring.messaging.CloudEventMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudEventMessageConverterConfiguration {
    @Bean
    public CloudEventMessageConverter cloudEventMessageConverter() {
        return new CloudEventMessageConverter();
    }
}

