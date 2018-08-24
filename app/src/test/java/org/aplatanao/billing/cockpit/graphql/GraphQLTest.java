package org.aplatanao.billing.cockpit.graphql;

import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.aplatanao.billing.cockpit.clients.GraphQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.socket.PortFactory;
import org.mockserver.verify.VerificationTimes;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class GraphQLTest {

    private static final String PATH = "/graphql";

    private MockServerClient server;

    private GraphQL client;

    private String mockedSchema() throws IOException {
        URI uri = new ClassPathResource("/schema.json").getURI();
        return new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);
    }

    @Before
    public void initClientMock() throws IOException, URISyntaxException {
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
        client = new GraphQL(new URIBuilder().setScheme("http")
                .setHost(server.remoteAddress().getHostString())
                .setPort(server.remoteAddress().getPort())
                .setPath(PATH).build());
    }

    @After
    public void verifyClientInit() {
        server.verify(HttpRequest.request().withPath(PATH), VerificationTimes.once());
        server.stop();
    }

    @Test
    public void initializedClient() {
        assertThat(client.getTypes(), hasItem(hasProperty("name", equalTo("Invoice"))));
        assertThat(client.getQueries(), hasItem(hasProperty("name", equalTo("invoices"))));
    }
}
