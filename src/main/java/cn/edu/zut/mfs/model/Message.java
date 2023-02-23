package cn.edu.zut.mfs.model;

import cn.edu.zut.mfs.domain.RabbitMQMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    // 消息来源
    private String origin;
    // 消息传递样式
    private String interaction;
    // 消息序号
    private long index;
    private long created = Instant.now().getEpochSecond();
    private int count;
    private String exchange;
    private String queue;
    private List<RabbitMQMessage> rabbitMQMessages;

    public Message(String origin, String interaction) {
        this.origin = origin;
        this.interaction = interaction;
        this.index = 0;
    }

    public Message(String origin, String interaction, long index) {
        this.origin = origin;
        this.interaction = interaction;
        this.index = index;
    }
}
