package org.aplatanao.graphql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Client {

    protected ObjectMapper mapper = new ObjectMapper();

    protected CloseableHttpClient client = HttpClients.createDefault();

    private URI endpoint;

    public API api;

    private List<Query> queries = new ArrayList<>();

    private List<Type> types = new ArrayList<>();

    private boolean initialized = false;

    private String status;

    private String getSchemaQuery() throws IOException, URISyntaxException {
        URI uri = this.getClass().getResource("/schema.graphql").toURI();
        return new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);
    }

    public Client(API api) throws URISyntaxException {
        this.api = api;
        this.endpoint = new URI(api.uri);
    }

    public void init() {
        try {
            ObjectNode node = execute(getSchemaQuery());
            JsonNode schema = node.path("data").path("__schema");

            for (JsonNode t : schema.path("types")) {
                types.add(mapper.treeToValue(t, Type.class));
            }
            for (JsonNode q : schema.path("queryType").path("fields")) {
                queries.add(mapper.treeToValue(q, Query.class));
            }
            initialized = true;
        } catch (IOException | URISyntaxException e) {
            initialized = false;
            status = e.getMessage();
            throw new RuntimeException(e);
        }
    }

    private CloseableHttpResponse execute(HttpRequestBase request) throws IOException {
        return client.execute(request);
    }

    public ObjectNode execute(String query) throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put("query", query);

        HttpPost post = new HttpPost(endpoint);
        post.setEntity(new StringEntity(mapper.writeValueAsString(node), Consts.UTF_8));
        post.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        CloseableHttpResponse response = execute(post);
        status = response.getStatusLine().toString();
        return mapper.readValue(response.getEntity().getContent(), ObjectNode.class);
    }

    public List<Query> getQueries() {
        return queries;
    }

    public Client setQueries(List<Query> queries) {
        this.queries = queries;
        return this;
    }

    public List<Type> getTypes() {
        return types;
    }

    public Client setTypes(List<Type> types) {
        this.types = types;
        return this;
    }


    public boolean isInitialized() {
        return initialized;
    }

    public String getStatus() {
        return status;
    }

    public API getAPI() {
        return api;
    }
}
