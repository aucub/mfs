package cn.edu.zut.mfs.domain;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "PublishRecord")
public class PublishRecord {
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "source")
    private URI source;
    @Column(name = "type")
    private String type;
    @Column(name = "appId")
    private String appId;
    @Column(name = "userId")
    private String userId;
    @Column(name = "priority")
    private int priority;
    @Column(name = "expiration")
    private String expiration = "0";
    @Column(name = "delay")
    private int delay;
    @Column(name = "publishingId")
    private long publishingId;
    @Column(name = "dataContentType")
    private String dataContentType = "application/octet-stream";
    @Column(name = "contentEncoding")
    private String contentEncoding = "text/plain";
    @Column(name = "subject")
    private String subject = "message";
    @Column(name = "body")
    private String body;
    @Column(name = "submit")
    private Boolean submit;
    @Column(timestamp = true)
    private Instant time;

    public PublishRecord(String messageId, URI source, String type, String appId, String userId, long publishingId, String dataContentType, String contentEncoding, String subject, String body, Boolean submit, Instant time) {
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

    public PublishRecord(String messageId, URI source, String type, String appId, String userId, int priority, String expiration, int delay, String dataContentType, String contentEncoding, String subject, String body, Instant time) {
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
