package org.aplatanao.graphql;

import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.socket.PortFactory;
import org.mockserver.verify.VerificationTimes;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class ClientTest {

    private static final String PATH = "/graphql";

    private MockServerClient server;

    private String mockedSchema() throws IOException, URISyntaxException {
        URL url = getClass().getResource("/schema.json");
        return new String(Files.readAllBytes(Paths.get(url.toURI())), StandardCharsets.UTF_8);
    }

    @BeforeEach
    public void initServer() throws IOException, URISyntaxException {
        server = startClientAndServer(PortFactory.findFreePort());
        server.when(HttpRequest.request()
                .withMethod("POST")
                .withHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withPath(PATH)
        ).respond(HttpResponse.response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(mockedSchema()));
    }

    @AfterEach
    public void verifyServer() {
        server.verify(HttpRequest.request().withPath(PATH), VerificationTimes.once());
        server.stop();
    }

    @Test
    public void initClient() throws URISyntaxException {
        URI uri = new URIBuilder().setScheme("http")
                .setHost(server.remoteAddress().getHostString())
                .setPort(server.remoteAddress().getPort())
                .setPath(PATH).build();
        Client client = new Client(new API("test_mock", uri.toString(), "local mocked API for testing"));
        client.init();
        assertTrue(client.isInitialized());
        assertThat(client.getStatus(), notNullValue());
        assertThat(client.getType("String"), notNullValue());
        assertThat(client.getQuery("job"), notNullValue());
    }
}
