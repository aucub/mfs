package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
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
    public void publishPoint(String messageId, Boolean result) {
        Point point = Point.measurement("PublishRecord")
                .addField("messageId", messageId)
                .addField("submit", result)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);
    }

    @Override
    public void consume(ConsumeRecord consumeRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, consumeRecord);
    }

    @Override
    public void queryPublish() {
        String flux = "from(bucket:\"mfs\") |> range(start: 50) |> filter(fn: (r) => r._measurement == \"PublishRecord\")";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<PublishRecord> publishRecords = queryApi.query(flux, PublishRecord.class);
        for (PublishRecord publishRecord : publishRecords) {
            System.out.println("------------------------------------------" + publishRecord.getPublishingId());
        }
    }

    @Override
    public void close() {
        this.influxDBClient.close();
    }
}
