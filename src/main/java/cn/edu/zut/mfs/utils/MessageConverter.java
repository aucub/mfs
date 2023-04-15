package cn.edu.zut.mfs.utils;

import io.cloudevents.core.data.BytesCloudEventData;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.net.URI;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageConverter {
    public Message toMessage(CloudEventV1 payload) {
        if (payload != null) {
            Map<String, Object> headers=new HashMap<>();
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setMessageId(payload.getId());
            messageProperties.setContentType(payload.getDataContentType());
            messageProperties.setType(payload.getType());
            messageProperties.setTimestamp(Date.from(payload.getTime().toInstant()));
            payload.getExtensionNames().forEach(item->{
                switch (item) {
                    case "userId" -> messageProperties.setUserId((String) payload.getExtension("userId"));
                    case "appId" -> messageProperties.setAppId((String) payload.getExtension("appId"));
                    case "correlationId" ->
                            messageProperties.setCorrelationId((String) payload.getExtension("correlationId"));
                    case "replyTo" -> messageProperties.setReplyTo((String) payload.getExtension("replyTo"));
                    case "contentEncoding" ->
                            messageProperties.setContentEncoding((String) payload.getExtension("contentEncoding"));
                    case "expiration" -> messageProperties.setExpiration((String) payload.getExtension("expiration"));
                    default -> headers.put(item, payload.getExtension(item));
                }
            });
            messageProperties.setHeaders(headers);
            return new Message(payload.getData().toBytes(), messageProperties);
        }
        return null;
    }
    public CloudEventV1 fromMessage(Message payload){
        URI source=payload.getMessageProperties().getHeader("source");
        URI dataschema=payload.getMessageProperties().getHeader("dataschema");
        String subject=payload.getMessageProperties().getHeader("subject");
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
}
