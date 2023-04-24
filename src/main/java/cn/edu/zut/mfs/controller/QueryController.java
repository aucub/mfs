package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.impl.InfluxDBServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/query/")
@RestController
public class QueryController {
    private InfluxDBService influxDBService = new InfluxDBServiceImpl();

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryPublish")
    public List<PublishRecord> queryPublish(String start, String stop) {
        return influxDBService.queryPublish(start, stop);
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryConsume")
    public List<ConsumeRecord> queryConsume(String start, String stop) {
        return influxDBService.queryConsume(start, stop);
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryPush")
    public List<PushMessage> queryPush(String start, String stop) {
        return influxDBService.queryPush(start, stop);
    }
}
