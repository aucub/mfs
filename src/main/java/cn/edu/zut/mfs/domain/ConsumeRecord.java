package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumeRecord {
    private String messageId;
    private long publishingId;
    private long offset;
    private String queue;
    private String userId;
}
