package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.ConsumerCustomizerFor;
import cn.edu.zut.mfs.MessageListenerFor;
import cn.edu.zut.mfs.controller.MessageController;
import cn.edu.zut.mfs.domain.ForwardMessage;
import cn.edu.zut.mfs.service.ConsumeStreamService;
import cn.edu.zut.mfs.service.PublishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.stream.ChannelCustomizer;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.rsocket.metadata.TaggingMetadataCodec;
import io.rsocket.util.ByteBufPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.rabbit.stream.listener.ConsumerCustomizer;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Service
public class ConsumeStreamServiceImpl implements ConsumeStreamService {

    private final static String stream = "mfs";
    private static Environment env = Environment.builder()
            .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2fmfs")
            .build();
    PublishService publishService;
    private RabbitStreamTemplate rabbitStreamTemplate;

    @Autowired
    public void setRabbitStreamTemplate(RabbitStreamTemplate rabbitStreamTemplate) {
        this.rabbitStreamTemplate = rabbitStreamTemplate;
    }

    @Autowired
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Override
    //@RabbitListener(queues = "mfs")//, containerFactory = "nativeFactory"
    public void consumer(String in) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ForwardMessage forwardMessage = mapper.readValue(in, ForwardMessage.class);
            if(MessageController.clients.containsKey(forwardMessage.getConsumer())) {
                RSocketRequester requester=MessageController.clients.get(forwardMessage.getConsumer());
                System.out.println(forwardMessage.getConsumer());
                ByteBuf routeMetadata = TaggingMetadataCodec
                        .createTaggingContent(ByteBufAllocator.DEFAULT, Collections.singletonList("status"));

                Mono.just("Server is calling you.")
//                .delayElement(Duration.ofSeconds(ThreadLocalRandom.current().nextInt(5, 10)))
                        .flatMap(m -> requester.rsocketClient().requestResponse(
                                        Mono.just(
                                                ByteBufPayload.create(
                                                        ByteBufUtil.writeUtf8(
                                                                ByteBufAllocator.DEFAULT,
                                                                in),
                                                        routeMetadata)))
                                .doOnSubscribe(subscription -> log.info("Client subscribed."))
                                .doOnError(throwable -> log.error("Error when calling client: {}", throwable.toString()))
                                .doOnSuccess(p -> log.info("[test.connect.requester]Received from client: {}.", p.getDataUtf8()))
                        )
                        .subscribe();
            }
            else publishService.publish(forwardMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(in.toString());
    }

    public void consume() {
        /*Environment environment = Environment.builder()
                .uri("rabbitmq-stream://root:root@47.113.201.150:5552/%2f")
                .build();
        StreamListenerContainer streamListenerContainer=new StreamListenerContainer(environment);
        streamListenerContainer.setQueueNames("mfs");
        streamListenerContainer.setupMessageListener(new MessageListenerFor());
        streamListenerContainer.setConsumerCustomizer(new ConsumerCustomizerFor());
        streamListenerContainer.setAutoStartup(true);
        streamListenerContainer.start();
        try {
            wait(100000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        Consumer consumer =
                env.consumerBuilder()
                        .stream(stream)
                        .name("application-1")
                        .offset(OffsetSpecification.first())
                        .autoTrackingStrategy()
                        .builder()
                        .messageHandler((context, message) -> {
                            System.out.println(message.getBody());
                        })
                        .build();
        consumer.storedOffset();
        try {
            wait(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
