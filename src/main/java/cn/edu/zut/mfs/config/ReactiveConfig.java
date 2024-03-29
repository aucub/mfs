package cn.edu.zut.mfs.config;

import cn.edu.zut.mfs.domain.MetadataHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.MimeType;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;


/**
 * rsocket配置
 */
@Configuration
@EnableRSocketSecurity
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ReactiveConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        try {
            http
                    .csrf().disable()
                    .cors().disable()
                    .authorizeExchange(
                            authorize -> {
                                authorize.pathMatchers("/login/doLogin", "/register/user").permitAll();
                                authorize.pathMatchers("/rsocket/**", "/actuator/**").permitAll();
                                authorize.anyExchange().authenticated();
                            }
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> {
                                        try {
                                            jwt
                                                    .authenticationManager(jwtReactiveAuthenticationManager(reactiveJwtDecoder()));
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            )
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return http.build();
    }

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {
        security.authorizePayload(authorize ->
                authorize
                        .setup().permitAll()
                        .anyExchange().permitAll()
                        .anyRequest().permitAll()
        ).jwt(jwtSpec -> {
            try {
                jwtSpec.authenticationManager(jwtReactiveAuthenticationManager(reactiveJwtDecoder()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return security.build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec("JAC1O17W1F3QB9E8B4B1MT6QKYOQB36V".getBytes(), mac.getAlgorithm());
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager(ReactiveJwtDecoder reactiveJwtDecoder) {
        JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager = new JwtReactiveAuthenticationManager(reactiveJwtDecoder);
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        jwtReactiveAuthenticationManager.setJwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(authenticationConverter));
        return jwtReactiveAuthenticationManager;
    }

    @Bean
    @Order(-1)
    public RSocketStrategies rsocketStrategies() {
        return RSocketStrategies.builder()
                .metadataExtractorRegistry(registry -> {
                    registry.metadataToExtract(
                            MimeType.valueOf("application/x.metadata+json"),
                            new ParameterizedTypeReference<Map<String, String>>() {
                            },
                            (jsonMap, outputMap) -> outputMap.putAll(jsonMap));
                    registry.metadataToExtract(MimeType.valueOf("application/x.metadataHeader+json"), MetadataHeader.class, "metadataHeader");
                })
                .encoders(encoders -> {
                    encoders.add(new io.cloudevents.spring.codec.CloudEventEncoder());
                    encoders.add(new Jackson2CborEncoder());
                    encoders.add(new Jackson2JsonEncoder());
                    encoders.add(new SimpleAuthenticationEncoder());
                })
                .decoders(decoders -> {
                    decoders.add(new io.cloudevents.spring.codec.CloudEventDecoder());
                    decoders.add(new Jackson2CborDecoder());
                    decoders.add(new Jackson2JsonDecoder());
                })
                .routeMatcher(new PathPatternRouteMatcher())
                .dataBufferFactory(new DefaultDataBufferFactory(true))
                .build();
    }

}
