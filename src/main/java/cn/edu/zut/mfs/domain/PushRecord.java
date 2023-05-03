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
@Measurement(name = "PushRecord")
public class PushRecord {
    @Column
    private String userId;
    @Column
    private String route;
    @Column
    private String body;
    @Column
    private String res;
    @Column
    private String id;
    @Column(timestamp = true)
    private Instant time;
    private String date;

    public PushRecord(String userId, String route, String body, String id, Instant time) {
        this.userId = userId;
        this.route = route;
        this.body = body;
        this.id = id;
        this.time = time;
    }

    public PushRecord(String userId, String route, String body, String res, String id, Instant time) {
        this.userId = userId;
        this.route = route;
        this.body = body;
        this.res = res;
        this.id = id;
        this.time = time;
    }
}
