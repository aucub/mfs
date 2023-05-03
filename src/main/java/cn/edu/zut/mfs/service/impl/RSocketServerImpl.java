package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.domain.PushRecord;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.RSocketServer;
import cn.edu.zut.mfs.service.RedisService;
import cn.edu.zut.mfs.service.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class RSocketServerImpl implements RSocketServer {

    private RedisService redisService;

    private InfluxDBService influxDBService = new InfluxDBServiceImpl();

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String push(PushMessage pushMessage) {
        if (redisService.hasKey("rsocket", pushMessage.getUserId())) {
            RSocketRequester requester = RequestProcessor.nonBlockingHashMap.get(pushMessage.getUserId());
            AtomicReference<String> res = new AtomicReference<>("");
            try {
                requester.route(pushMessage.getRoute()).data(pushMessage.getBody()).retrieveMono(String.class).subscribe(r -> {
                    res.set(r.toString());
                    influxDBService.push(new PushRecord(pushMessage.getUserId(), pushMessage.getRoute(), pushMessage.getBody(), r.toString(), pushMessage.getId(), Instant.now()));
                });
            } catch (Exception e) {
                influxDBService.push(new PushRecord(pushMessage.getUserId(), pushMessage.getRoute(), pushMessage.getBody(), null, pushMessage.getId(), Instant.now()));
            }
            return res.get().toString();
        }
        return "";
    }
}
