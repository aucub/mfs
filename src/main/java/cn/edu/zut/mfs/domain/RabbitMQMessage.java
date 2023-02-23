package cn.edu.zut.mfs.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class RabbitMQMessage {
    // 消息传递样式
    private String interaction;
    // 消息序号
    private long index;
    private long created = Instant.now().getEpochSecond();
    private String content;
}
