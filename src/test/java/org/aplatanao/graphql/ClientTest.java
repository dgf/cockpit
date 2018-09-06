package org.aplatanao.graphql;

import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.socket.PortFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ClientTest {

    private static final String PATH = "/graphql";

    private static MockServerClient server;

    private static URI uri;

    private static String schema;

    private String readSchema(String resource) throws IOException, URISyntaxException {
        URL url = getClass().getResource(resource);
        return new String(Files.readAllBytes(Paths.get(url.toURI())), StandardCharsets.UTF_8);
    }

    @BeforeAll
    public static void startServer() throws URISyntaxException {
        server = startClientAndServer(PortFactory.findFreePort());
        uri = new URIBuilder().setScheme("http")
                .setHost(server.remoteAddress().getHostString())
                .setPort(server.remoteAddress().getPort())
                .setPath(PATH).build();
        server.when(request()
                .withMethod("POST")
                .withHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withPath(PATH)
        ).respond(request -> response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(schema));

    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    public void etmb() throws URISyntaxException, IOException {
        schema = readSchema("/etmdb.schema.json");
        Client client = new Client(new API("test_etmb", uri.toString(), "mocked"));
        client.init();
        assertTrue(client.isInitialized());
        assertThat(client.getStatus(), notNullValue());
        assertThat(client.getType("String"), notNullValue());
        assertThat(client.getQuery("job"), notNullValue());

        Type node = client.getType("Node");
        assertThat(node, notNullValue());
        assertThat(node.getName(), is("Node"));
        assertThat(node.getKind(), is("INTERFACE"));
        assertThat(node.getFields(), hasItem(hasProperty("name", is("id"))));

        Type newsNodeConnection = client.getType("NewsNodeConnection");
        assertThat(newsNodeConnection, notNullValue());
        assertThat(newsNodeConnection.getName(), is("NewsNodeConnection"));
        assertThat(newsNodeConnection.getKind(), is("OBJECT"));

        Field edges = newsNodeConnection.getFields().stream()
                .filter(f -> f.getName().equals("edges"))
                .findFirst().orElse(null);
        assertThat(edges, notNullValue());

        OfType edgeOfType = edges.getType().getOfType();
        assertThat(edgeOfType, notNullValue());
        assertThat(edgeOfType.getKind(), is("LIST"));

        OfType listOfType = edgeOfType.getOfType();
        assertThat(listOfType, notNullValue());
        assertThat(listOfType.getKind(), is("OBJECT"));
        assertThat(listOfType.getName(), is("NewsNodeEdge"));
    }

    @Test
    public void gdom() throws URISyntaxException, IOException {
        schema = readSchema("/gdom.schema.json");
        Client client = new Client(new API("test_gdom", uri.toString(), "mocked"));
        client.init();
        assertTrue(client.isInitialized());
        assertThat(client.getStatus(), notNullValue());
        assertThat(client.getQuery("page"), notNullValue());

        Type node = client.getType("Node");
        assertThat(node, notNullValue());
        assertThat(node.getName(), is("Node"));
        assertThat(node.getKind(), is("INTERFACE"));
        assertThat(node.getFields(), hasItem(hasProperty("name", is("content"))));

        Type document = client.getType("Document");
        assertThat(document, notNullValue());
        assertThat(document.getName(), is("Document"));
        assertThat(document.getKind(), is("OBJECT"));
        assertThat(document.getFields(), hasItem(hasProperty("name", is("content"))));
    }
}
