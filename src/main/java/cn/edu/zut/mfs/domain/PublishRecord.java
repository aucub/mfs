package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishRecord {
    private String messageId;
    private String appId;
    private String userId;
    private int priority;
    private String correlationId;
    private String expiration;
    private String exchange;
    private int delay;
    private long publishingId;
    private String routingKey;
    private String queueType;
    private long offset;
    private byte[] body;
}
