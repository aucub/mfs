package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.domain.PublishRecord;
import cn.edu.zut.mfs.domain.PushMessage;
import cn.edu.zut.mfs.domain.Search;
import cn.edu.zut.mfs.service.InfluxDBService;
import cn.edu.zut.mfs.service.MeiliSearchService;
import cn.edu.zut.mfs.service.impl.InfluxDBServiceImpl;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Searchable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequestMapping("/query")
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
    public Mono<Searchable> search(@RequestBody Search search) throws MeilisearchException {
        return Mono.just(MeiliSearchService.search(search.getUid(), search.getKeyword(), search.getOffset()));
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/tran")
    public Mono<Void> tran(String uid, String start, String stop) {
        Thread.startVirtualThread(() -> {
            switch (uid) {
                case "PublishRecord" -> influxDBService.tranPublish(start, stop);
                case "ConsumeRecord" -> influxDBService.tranConsume(start, stop);
                case "PushMessage" -> influxDBService.tranPush(start, stop);
            }
        });
        return Mono.empty();
    }

    @PreAuthorize("hasRole('query')")
    @PostMapping("/tranScheduled")
    @Scheduled(initialDelay = 1000 * 60 * 60, fixedRate = 1000 * 60 * 60 * 6)
    public Mono<Void> tranScheduled() {
        Thread.startVirtualThread(() -> {
            influxDBService.tranPublish("-70d", "0d");
            influxDBService.tranConsume("-6h", "0h");
            influxDBService.tranPush("-6h", "0h");
        });
        return Mono.empty();
    }
}
