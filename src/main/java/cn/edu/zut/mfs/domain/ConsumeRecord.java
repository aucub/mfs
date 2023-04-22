package cn.edu.zut.mfs.domain;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "ConsumeRecord")
public class ConsumeRecord {
    @Column
    private String messageId;
    @Column
    private long publishingId;
    @Column
    private long offset;
    @Column
    private String queue;
    @Column
    private String userId;
    @Column(timestamp = true)
    private Instant time;

    public ConsumeRecord(String messageId, String queue, String userId, Instant time) {
        this.messageId = messageId;
        this.queue = queue;
        this.userId = userId;
        this.time = time;
    }
}
