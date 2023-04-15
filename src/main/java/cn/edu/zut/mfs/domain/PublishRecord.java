package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishRecord {
    private String messageId;
    private String userId;
    private String exchange;
    private String routingKey;
    private String queueType;
    private int offset;
}
