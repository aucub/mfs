package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.service.InfluxDBService;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.stereotype.Service;


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
    public void consume(ConsumeRecord consumeRecord) {
        writeApi.writeMeasurement(WritePrecision.NS, consumeRecord);
    }

    @Override
    public void close() {
        this.influxDBClient.close();
    }
}
