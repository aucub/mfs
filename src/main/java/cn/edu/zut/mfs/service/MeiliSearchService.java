package cn.edu.zut.mfs.service;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import org.springframework.stereotype.Service;

@Service
public class MeiliSearchService {
    private static Client client = new Client(new Config("http://localhost:7700", "root"));

    public static void store(String document, String uid) throws MeilisearchException {
        Index index = client.index(uid);
        index.addDocuments(document);
    }
}
