package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushRecord;
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

    private static final InfluxDBClient influxDBClient = InfluxDBClientFactory.create();
    private static final WriteApi writeApi = influxDBClient.makeWriteApi();
    private final static ObjectMapper mapper = new ObjectMapper();
    InfluxDBClientReactive influxDBClientReactive = InfluxDBClientReactiveFactory.create();
    QueryReactiveApi queryReactiveApi = influxDBClientReactive.getQueryReactiveApi();

    @Override
    public void publish(PublishRecord publishRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, publishRecord);
    }

    @Override
    public void push(PushRecord pushRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, pushRecord);
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
    public List<PushRecord> queryPush(String start, String stop) {
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PushRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")" +
                " |> limit(n:120)";
        String flux = String.format(template, start, stop);
        QueryApi queryApi = influxDBClient.getQueryApi();
        return queryApi.query(flux, PushRecord.class);
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
                .take(100)
                .subscribe(publishRecord -> {
                    publishRecord.setDate(publishRecord.getTime().toString());
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
                .take(100)
                .subscribe(consumeRecord -> {
                    consumeRecord.setDate(consumeRecord.getTime().toString());
                    MeiliSearchService.store(mapper.writeValueAsString(consumeRecord), "ConsumeRecord");
                });
    }

    @Override
    public void tranPush(String start, String stop) {
        mapper.registerModule(new JavaTimeModule());
        String template = "from(bucket:\"mfs\") " +
                " |> range(start: %s, stop: %s)" +
                " |> filter(fn: (r) => r._measurement == \"PushRecord\")" +
                " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
        String flux = String.format(template, start, stop);
        Publisher<PushRecord> query = queryReactiveApi.query(flux, PushRecord.class);
        Flowable.fromPublisher(query)
                .subscribe(pushRecord -> {
                    pushRecord.setDate(pushRecord.getTime().toString());
                    MeiliSearchService.store(mapper.writeValueAsString(pushRecord), "PushRecord");
                });
    }

}
