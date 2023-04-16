package cn.edu.zut.mfs.utils;

import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.rabbit.stream.support.StreamMessageProperties;

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
                    case "userid" -> messageProperties.setUserId((String) payload.getExtension("userid"));
                    case "appid" -> messageProperties.setAppId((String) payload.getExtension("appid"));
                    case "priority" -> messageProperties.setPriority((Integer) payload.getExtension("priority"));
                    case "correlationid" ->
                            messageProperties.setCorrelationId((String) payload.getExtension("correlationid"));
                    case "replyto" -> messageProperties.setReplyTo((String) payload.getExtension("replyto"));
                    case "contentencoding" ->
                            messageProperties.setContentEncoding((String) payload.getExtension("contentencoding"));
                    case "expiration" -> messageProperties.setExpiration((String) payload.getExtension("expiration"));
                    case "x-delay" -> messageProperties.setDelay((Integer) payload.getExtension("x-delay"));
                    default -> headers.put(item, payload.getExtension(item));
                }
            });
            messageProperties.setHeaders(headers);
            return new Message(payload.getData().toBytes(), messageProperties);
        }
        return null;
    }

    public static Message toStreamMessage(CloudEventV1 payload) {
        if (payload != null) {
            Map<String, Object> headers = new HashMap<>();
            StreamMessageProperties streamMessageProperties = new StreamMessageProperties();
            return new Message(payload.getData().toBytes(), streamMessageProperties);
        }
        return null;
    }

    public static CloudEventV1 fromMessage(Message payload) {
        URI source = payload.getMessageProperties().getHeader("source");
        URI dataschema = payload.getMessageProperties().getHeader("dataschema");
        String subject = payload.getMessageProperties().getHeader("subject");
        payload.getMessageProperties().getHeaders().remove("source");
        payload.getMessageProperties().getHeaders().remove("dataschema");
        payload.getMessageProperties().getHeaders().remove("subject");
        /*return CloudEventBuilder.v1().withId(payload.getMessageProperties().getMessageId())
                .withDataContentType(payload.getMessageProperties().getContentType())
                .withTime(payload.getMessageProperties().getTimestamp().toInstant().atOffset(ZoneOffset.UTC))
                .withSource(uri)
                .withType(payload.getMessageProperties().getType())
                .withData(payload.getBody());*/
        return new CloudEventV1(payload.getMessageProperties().getMessageId(), source, payload.getMessageProperties().getType(), payload.getMessageProperties().getContentType(), dataschema, subject, payload.getMessageProperties().getTimestamp().toInstant().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap(payload.getBody()), payload.getMessageProperties().getHeaders());
    }

    public static CloudEventV1 fromStreamMessage(Message payload) {
        return new CloudEventV1(UUID.randomUUID().toString(), URI.create("https://spring.io/foos"), "com.github.pull.create", "text/plain", URI.create("test"), "test", Instant.now().atOffset(ZoneOffset.UTC), BytesCloudEventData.wrap(payload.getBody()), null);
    }
}
