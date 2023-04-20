package cn.edu.zut.mfs.utils;

import cn.edu.zut.mfs.config.EventExtension;
import com.rabbitmq.stream.MessageHandler;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageConverter {
    public static Message toMessage(CloudEventV1 payload) {
        if (payload != null) {
            Map<String, Object> headers = new HashMap<>();
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setMessageId(payload.getId());
            messageProperties.setContentType(payload.getDataContentType());
            messageProperties.setType(payload.getType());
            messageProperties.setTimestamp(Date.from(payload.getTime().toInstant()));
            payload.getExtensionNames().forEach(item -> {
                switch (item) {
                    case "appid" -> messageProperties.setAppId((String) payload.getExtension("appid"));
                    case "priority" -> messageProperties.setPriority((Integer) payload.getExtension("priority"));
                    case "contentencoding" ->
                            messageProperties.setContentEncoding((String) payload.getExtension("contentencoding"));
                    case "expiration" ->
                            messageProperties.setExpiration(String.valueOf(payload.getExtension("expiration")));
                    case "delay" -> messageProperties.setDelay((Integer) payload.getExtension("delay"));
                    default -> headers.put(item, payload.getExtension(item));
                }
            });
            return new Message(payload.getData().toBytes(), messageProperties);
        }
        return null;
    }


    public static CloudEventV1 fromMessage(Message payload) {
        URI source = URI.create("");
        if (payload.getMessageProperties().getHeader("source") != null) {
            source = URI.create(payload.getMessageProperties().getHeader("source"));
            payload.getMessageProperties().getHeaders().remove("source");
        }
        String subject = payload.getMessageProperties().getMessageId();
        if (payload.getMessageProperties().getHeader("subject") != null) {
            subject = payload.getMessageProperties().getHeader("subject");
            payload.getMessageProperties().getHeaders().remove("subject");
        }
        return new CloudEventV1(payload.getMessageProperties().getMessageId(), source, payload.getMessageProperties().getType(), payload.getMessageProperties().getContentType(), URI.create("data"), subject, payload.getMessageProperties().getTimestamp().toInstant().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap(payload.getBody()), payload.getMessageProperties().getHeaders());
    }

    public static CloudEventV1 fromStreamMessage(MessageHandler.Context context, com.rabbitmq.stream.Message payload) {
        final EventExtension eventExtension = new EventExtension();
        eventExtension.setPublishingid(payload.getPublishingId());
        eventExtension.setOffset(context.offset());
        return (CloudEventV1) CloudEventBuilder.v1()
                .withDataContentType("text/plain")
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("http://example.com/mfs"))
                .withType("com.example.mfs")
                .withTime(Instant.ofEpochSecond(context.offset()).atOffset(ZoneOffset.UTC))
                .withData(BytesCloudEventData.wrap(payload.getBodyAsBinary()))
                .withExtension(eventExtension)
                .build();
        //return new CloudEventV1(UUID.randomUUID().toString(), URI.create("http://example.com/mfs"), "com.example.mfs", "text/plain", URI.create("mfs"), "mfs", Instant.now().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap(payload.getBodyAsBinary()), null);
    }
}
