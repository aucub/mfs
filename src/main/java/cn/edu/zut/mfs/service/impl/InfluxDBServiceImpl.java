package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.MeiliSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.reactive.InfluxDBClientReactive;
import com.influxdb.client.reactive.InfluxDBClientReactiveFactory;
import com.influxdb.client.reactive.QueryReactiveApi;
import com.influxdb.client.write.Point;
import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class InfluxDBServiceImpl implements InfluxDBService {
    private static final InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://127.0.0.1:8086", "UV6YDazxbkL4Oda9q4h6eUMUGIRUWMAPcjqPrb1VCNB5QwR-340Xd1WPyQqufT2hmBUflIy8gN71cHz686HrTg==".toCharArray(), "example", "mfs");
    private static final WriteApi writeApi = influxDBClient.makeWriteApi();

    InfluxDBClientReactive influxDBClientReactive = InfluxDBClientReactiveFactory.create("http://127.0.0.1:8086", "UV6YDazxbkL4Oda9q4h6eUMUGIRUWMAPcjqPrb1VCNB5QwR-340Xd1WPyQqufT2hmBUflIy8gN71cHz686HrTg==".toCharArray(), "example", "mfs");

    QueryReactiveApi queryReactiveApi = influxDBClientReactive.getQueryReactiveApi();


    private final static ObjectMapper mapper = new ObjectMapper();

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
        influxDBClient.close();
    }

    @Override
    public void tranPublish(String start, String stop) {
        mapper.registerModule(new JavaTimeModule());
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PublishRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
        String flux = String.format(template, start, stop);
        Publisher<PublishRecord> query = queryReactiveApi.query(flux, PublishRecord.class);
        Flowable.fromPublisher(query)
                .take(10)
                .subscribe(publishRecord -> {
                    MeiliSearchService.store(mapper.writeValueAsString(publishRecord), "PublishRecord");
                });
    }

    @Override
    public void tranConsume(String start, String stop) {
        mapper.registerModule(new JavaTimeModule());
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"ConsumeRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
        String flux = String.format(template, start, stop);
        Publisher<ConsumeRecord> query = queryReactiveApi.query(flux, ConsumeRecord.class);
        Flowable.fromPublisher(query)
                .take(10)
                .subscribe(consumeRecord -> MeiliSearchService.store(mapper.writeValueAsString(consumeRecord), "ConsumeRecord"));
    }

    @Override
    public void tranPush(String start, String stop) {
        mapper.registerModule(new JavaTimeModule());
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PushMessage\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
        String flux = String.format(template, start, stop);
        Publisher<PushMessage> query = queryReactiveApi.query(flux, PushMessage.class);
        Flowable.fromPublisher(query)
                .take(10)
                .subscribe(pushMessage -> MeiliSearchService.store(mapper.writeValueAsString(pushMessage), "PushMessage"));
    }

}
