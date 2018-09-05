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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Client {

    private ObjectMapper mapper = new ObjectMapper();

    private CloseableHttpClient client = HttpClients.createDefault();

    private URI endpoint;

    public API api;

    private Map<String, QueryType> queries = new HashMap<>();

    private Map<String, Type> types = new HashMap<>();

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
                Type type = mapper.treeToValue(t, Type.class);
                types.put(type.getName(), type);
            }
            for (JsonNode q : schema.path("queryType").path("fields")) {
                QueryType queryType = mapper.treeToValue(q, QueryType.class);
                queries.put(queryType.getName(), queryType);
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

    public URI getEndpoint() {
        return endpoint;
    }

    public Client setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public API getApi() {
        return api;
    }

    public Client setApi(API api) {
        this.api = api;
        return this;
    }

    public Set<String> getQueries() {
        return queries.keySet();
    }

    public QueryType getQuery(String name) {
        return queries.get(name);
    }

    public Set<String> getTypes() {
        return types.keySet();
    }

    public Type getType(String name) {
        return types.get(name);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getStatus() {
        return status;
    }

}
