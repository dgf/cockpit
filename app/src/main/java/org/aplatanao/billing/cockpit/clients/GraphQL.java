package org.aplatanao.billing.cockpit.clients;

import com.dooapp.fxform.annotation.NonVisual;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.aplatanao.billing.cockpit.models.Query;
import org.aplatanao.billing.cockpit.models.Type;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GraphQL extends Client {

    @NonVisual
    private URI endpoint;

    private ListProperty<Query> queries = new SimpleListProperty<>(FXCollections.observableArrayList());

    @NonVisual
    private List<Type> types = new ArrayList<>();

    private String getSchemaQuery() throws IOException {
        URI uri = new ClassPathResource("/schema.graphql").getURI();
        return new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);
    }

    @Override
    public void init(URI endpoint) throws IOException {
        this.endpoint = endpoint;
        ObjectNode node = execute(getSchemaQuery());
        JsonNode schema = node.path("data").path("__schema");

        for (JsonNode t : schema.path("types")) {
            types.add(mapper.treeToValue(t, Type.class));
        }
        for (JsonNode q : schema.path("queryType").path("fields")) {
            queries.add(mapper.treeToValue(q, Query.class));
        }
    }

    public ObjectNode execute(String query) throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put("query", query);

        HttpPost post = new HttpPost(endpoint);
        post.setEntity(new StringEntity(mapper.writeValueAsString(node), Consts.UTF_8));
        post.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        CloseableHttpResponse response = execute(post);
        updateStatus(response);
        return mapper.readValue(response.getEntity().getContent(), ObjectNode.class);
    }

    public List<Query> getQueries() {
        return queries.get();
    }

    public ListProperty<Query> queriesProperty() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries.clear();
        this.queries.addAll(queries);
    }

    public List<Type> getTypes() {
        return types;
    }

}
