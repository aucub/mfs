package cn.edu.zut.mfs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.util.MimeType;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;


@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class RSocketSecurityConfig {
    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {
        security.authorizePayload(authorize ->
                authorize
                        .setup().permitAll() // blocks connections
                        .anyExchange().permitAll() // blocks connections
                        .anyRequest().permitAll() // blocks all other requests
        ).simpleAuthentication(Customizer.withDefaults());
        return security.build();
    }

    @Bean
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
                .metadataExtractorRegistry(registry -> {
                    registry.metadataToExtract(MimeType.valueOf("message/x.hello.trace"), String.class, "traceId");
                })
                .encoders(encoders -> {
                    encoders.add(new Jackson2CborEncoder());
                    encoders.add(new Jackson2JsonEncoder());
                })
                .decoders(decoders -> {
                    decoders.add(StringDecoder.allMimeTypes());
                    decoders.add(new Jackson2CborDecoder());
                    decoders.add(new Jackson2JsonDecoder());
                })
                .routeMatcher(new PathPatternRouteMatcher())
                .dataBufferFactory(new DefaultDataBufferFactory(true))
                .build();
    }
}
