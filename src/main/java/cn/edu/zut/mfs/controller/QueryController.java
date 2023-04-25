package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.MeiliSearchService;
import cn.edu.zut.mfs.service.impl.InfluxDBServiceImpl;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Searchable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequestMapping("/query/")
@RestController
public class QueryController {
    private final InfluxDBService influxDBService = new InfluxDBServiceImpl();

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryPublish")
    public Mono<List<PublishRecord>> queryPublish(@RequestParam String start, @RequestParam String stop) {
        return Mono.just(influxDBService.queryPublish(start, stop));
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryConsume")
    public Mono<List<ConsumeRecord>> queryConsume(@RequestParam String start, @RequestParam String stop) {
        return Mono.just(influxDBService.queryConsume(start, stop));
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/queryPush")
    public Mono<List<PushMessage>> queryPush(@RequestParam String start, @RequestParam String stop) {
        return Mono.just(influxDBService.queryPush(start, stop));
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/search")
    public Mono<Searchable> search(String uid, String keyword, Integer offset) throws MeilisearchException {
        return Mono.just(MeiliSearchService.search(uid, keyword, offset));
    }
}
