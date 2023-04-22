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
@Measurement(name = "PublishRecord")
public class PublishRecord {
    @Column
    private String messageId;
    @Column
    private String source;
    @Column
    private String type;
    @Column
    private String appId;
    @Column
    private String userId;
    @Column
    private int priority;
    @Column
    private String expiration;
    @Column
    private int delay;
    @Column
    private long publishingId;
    @Column
    private String dataContentType = "application/octet-stream";
    @Column
    private String contentEncoding = "text/plain";
    @Column
    private String subject = "message";
    @Column
    private String body;
    @Column
    private Boolean submit;
    @Column(timestamp = true)
    private Instant time;

    public PublishRecord(String messageId, String source, String type, String appId, String userId, long publishingId, String dataContentType, String contentEncoding, String subject, String body, Boolean submit, Instant time) {
        this.messageId = messageId;
        this.source = source;
        this.type = type;
        this.appId = appId;
        this.userId = userId;
        this.publishingId = publishingId;
        this.dataContentType = dataContentType;
        this.contentEncoding = contentEncoding;
        this.subject = subject;
        this.body = body;
        this.submit = submit;
        this.time = time;
    }

    public PublishRecord(String messageId, String source, String type, String appId, String userId, int priority, String expiration, int delay, String dataContentType, String contentEncoding, String subject, String body, Instant time) {
        this.messageId = messageId;
        this.source = source;
        this.type = type;
        this.appId = appId;
        this.userId = userId;
        this.priority = priority;
        this.expiration = expiration;
        this.delay = delay;
        this.dataContentType = dataContentType;
        this.contentEncoding = contentEncoding;
        this.subject = subject;
        this.body = body;
        this.time = time;
    }
}
