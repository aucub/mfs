package cn.edu.zut.mfs.config;

import io.cloudevents.spring.webflux.CloudEventHttpMessageReader;
import io.cloudevents.spring.webflux.CloudEventHttpMessageWriter;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.CodecConfigurer;

/**
 * CloudEventHandlerConfiguration
 *
 */
@Configuration
public class CloudEventHandlerConfiguration implements CodecCustomizer {

    @Override
    public void customize(CodecConfigurer configurer) {
        configurer.customCodecs().register(new CloudEventHttpMessageReader());
        configurer.customCodecs().register(new CloudEventHttpMessageWriter());
    }

}

