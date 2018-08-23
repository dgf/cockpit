package org.aplatanao.billing.cockpit.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.aplatanao.billing.cockpit.models.Query;
import org.aplatanao.billing.cockpit.models.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphQL {

    private ObjectMapper mapper = new ObjectMapper();

    private CloseableHttpClient client = HttpClients.createDefault();

    private URI endpoint;

    private List<Query> queries;

    private List<Type> types;

    public GraphQL(URI endpoint) throws IOException {
        this.endpoint = endpoint;
        init();
    }

    private String getSchemaQuery() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/schema.graphql");
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    private void init() throws IOException {
        ObjectNode node = execute(getSchemaQuery());
        JsonNode schema = node.path("data").path("__schema");

        types = new ArrayList<>();
        schema.path("types").forEach(q -> {
            try {
                types.add(mapper.treeToValue(q, Type.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        queries = new ArrayList<>();
        schema.path("queryType").path("fields").forEach(q -> {
            try {
                queries.add(mapper.treeToValue(q, Query.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObjectNode execute(String query) throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put("query", query);

        HttpPost post = new HttpPost(endpoint);
        post.setEntity(new StringEntity(mapper.writeValueAsString(node), Consts.UTF_8));
        post.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        CloseableHttpResponse response = client.execute(post);
        return mapper.readValue(response.getEntity().getContent(), ObjectNode.class);
    }

    public List<Query> getQueries() {
        return queries;
    }

    public List<Type> getTypes() {
        return types;
    }
}
