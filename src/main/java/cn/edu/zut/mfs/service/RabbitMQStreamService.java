package cn.edu.zut.mfs.service;

public class RabbitMQStreamService {
    /*private static String stream = UUID.randomUUID().toString();
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/mfs")
            .build();
    @Bean
    RabbitStreamTemplate streamTemplate() {
        env.streamCreator()
                .stream(stream)
                .maxLengthBytes(ByteCapacity.GB(1))
                .maxSegmentSizeBytes(ByteCapacity.MB(50))
                .create();
        RabbitStreamTemplate template = new RabbitStreamTemplate(env, stream);
        template.setProducerCustomizer((name, builder) -> builder.name("Producer"));
        return template;
    }

    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> rabbitListenerContainerFactory() {
        return new StreamRabbitListenerContainerFactory(env);
    }


    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory() {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(env);
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> builder.name("Consumer")
                .offset(OffsetSpecification.first())
                .manualTrackingStrategy());
        return factory;
    }*/

   /* @Bean
    public StreamRetryOperationsInterceptorFactoryBean sfb(RetryTemplate retryTemplate) {
        StreamRetryOperationsInterceptorFactoryBean rfb =
                new StreamRetryOperationsInterceptorFactoryBean();
        rfb.setRetryOperations(retryTemplate);
        rfb.setStreamMessageRecoverer((msg, context, throwable) -> {
        });
        return rfb;
    }*/

}
