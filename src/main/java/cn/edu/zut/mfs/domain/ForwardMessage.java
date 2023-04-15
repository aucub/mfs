package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessage {
    private Date timestamp;
    private String messageId;
    private String userId;
    private String appId;
    private String type;
    private String correlationId;
    private String replyTo;
    private String contentType;
    private String contentEncoding;
    private String expiration;
    private Integer priority = 0;
    private String exchange;
    private String queue;
    private String queueType;
    private byte[] body;
    private String client;
}
