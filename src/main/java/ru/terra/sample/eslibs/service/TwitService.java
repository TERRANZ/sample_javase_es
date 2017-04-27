package ru.terra.sample.eslibs.service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import ru.terra.sample.eslibs.domain.Twit;

import java.io.IOException;
import java.util.List;

public class TwitService {

    public static final String INDEX = "twit";
    public static final String TYPE = "twit";

    private JestClient elastic;

    public TwitService() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")
                .multiThreaded(false)
                .build());
        elastic = factory.getObject();
    }

    public List<Twit> findAll() throws IOException {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query)
                // multiple index or types can be added.
                .addIndex(INDEX)
                .addType(TYPE)
                .build();

        SearchResult result = elastic.execute(search);
        return result.getSourceAsObjectList(Twit.class);
    }

    public Twit findById(String id) throws IOException {
        JestResult result = elastic.execute(new Get.Builder(INDEX, id).build());
        return result.getSourceAsObject(Twit.class);
    }

    public void save(List<Twit> twits) throws IOException {
        twits.forEach(twit -> {
            try {
                elastic.execute(new Index.Builder(twit).index(INDEX).type(TYPE).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteAll() throws IOException {
        findAll().forEach(twit -> {
            try {
                elastic.execute(new Delete.Builder(twit.getGuid()).index(INDEX).type(TYPE).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Twit> findByText(String text) throws IOException {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"term\": {\n" +
                "      \"text\": \"" + text + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query)
                // multiple index or types can be added.
                .addIndex(INDEX)
                .addType(TYPE)
                .build();

        SearchResult result = elastic.execute(search);
        return result.getSourceAsObjectList(Twit.class);
    }

    public List<Twit> findByUserId(String uid) throws IOException {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"term\": {\n" +
                "      \"uid\": \"" + uid + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search = new Search.Builder(query)
                // multiple index or types can be added.
                .addIndex(INDEX)
                .addType(TYPE)
                .build();

        SearchResult result = elastic.execute(search);
        return result.getSourceAsObjectList(Twit.class);
    }
}
