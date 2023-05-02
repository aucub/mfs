package cn.edu.zut.mfs.service;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.json.JacksonJsonHandler;
import com.meilisearch.sdk.model.Searchable;
import org.springframework.stereotype.Service;

@Service
public class MeiliSearchService {
    private static final Client client = new Client(new Config("http://47.113.201.150:7700", "root", new JacksonJsonHandler()));

    public static void store(String document, String uid) throws MeilisearchException {
        Index index = client.index(uid);
        String primaryKey = null;
        switch (uid) {
            case "PublishRecord", "ConsumeRecord" -> primaryKey = "messageId";
            case "PushMessage" -> primaryKey = "id";
        }
        index.addDocuments(document, primaryKey);
    }

    public static Searchable search(String uid, String keyword, Integer offset) throws MeilisearchException {
        Index index = client.index(uid);
        return index.search(
                new SearchRequest(keyword)
                        .setShowMatchesPosition(true).setCropLength(500).setLimit(100).setOffset(offset)
        );
    }
}
