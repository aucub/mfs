package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.InfluxDBService;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class InfluxDBServiceImpl implements InfluxDBService {
    private final static InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://127.0.0.1:8086", "UV6YDazxbkL4Oda9q4h6eUMUGIRUWMAPcjqPrb1VCNB5QwR-340Xd1WPyQqufT2hmBUflIy8gN71cHz686HrTg==".toCharArray(), "example", "mfs");
    private final static WriteApi writeApi = influxDBClient.makeWriteApi();


    @Override
    public void publish(PublishRecord publishRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, publishRecord);
    }

    @Override
    public void push(PushMessage pushMessage) {
        Point point = Point.measurement("PushMessage")
                .addField("userId", pushMessage.getUserId())
                .addField("route", pushMessage.getRoute())
                .addField("body", new String(pushMessage.getBody()))
                .time(Instant.now(), WritePrecision.MS);
        writeApi.writePoint(point);
    }

    @Override
    public void publishPoint(String messageId, Boolean result, Instant time) {
        Point point = Point.measurement("PublishRecord")
                .addField("messageId", messageId)
                .addField("submit", result)
                .time(time, WritePrecision.MS);
        writeApi.writePoint(point);
    }

    @Override
    public void consume(ConsumeRecord consumeRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, consumeRecord);
    }

    @Override
    public List<PublishRecord> queryPublish(String start, String stop) {
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PublishRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")" +
                " |> limit(n:120)";
        String flux = String.format(template, start, stop);
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(flux, PublishRecord.class);
    }

    @Override
    public List<ConsumeRecord> queryConsume(String start, String stop) {
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"ConsumeRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")" +
                " |> limit(n:120)";
        String flux = String.format(template, start, stop);
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(flux, ConsumeRecord.class);
    }

    @Override
    public List<PushMessage> queryPush(String start, String stop) {
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PushMessage\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")" +
                " |> limit(n:120)";
        String flux = String.format(template, start, stop);
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(flux, PushMessage.class);
    }

    @Override
    public void close() {
        this.influxDBClient.close();
    }
}
