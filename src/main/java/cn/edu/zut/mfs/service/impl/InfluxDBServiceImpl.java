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
    private InfluxDBClient influxDBClient;
    private WriteApi writeApi;

    public InfluxDBServiceImpl() {
        this.influxDBClient = InfluxDBClientFactory.create();
        this.writeApi = influxDBClient.makeWriteApi();
    }

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
        List<PublishRecord> publishRecords = queryApi.query(flux, PublishRecord.class);
        return publishRecords;
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
        List<ConsumeRecord> consumeRecords = queryApi.query(flux, ConsumeRecord.class);
        return consumeRecords;
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
        List<PushMessage> pushMessages = queryApi.query(flux, PushMessage.class);
        return pushMessages;
    }

    @Override
    public void close() {
        this.influxDBClient.close();
    }
}
