package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * 消息
 */
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
