package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessage {
    private String publisher;
    private String consumer;
    private String client;
    private String messageId;
    private byte[] body;

    public ForwardMessage(String publisher, String consumer, byte[] body) {
        this.publisher = publisher;
        this.consumer = consumer;
        this.body = body;
    }
}
