package org.aplatanao.billing.cockpit.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.aplatanao.billing.cockpit.clients.GraphQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GraphQLTest {

    private static final String PATH = "/graphql";

    private ObjectMapper mapper = new ObjectMapper();

    @Rule
    public MockServerRule rule = new MockServerRule(this);

    private MockServerClient mock;

    private GraphQL client;

    private String mockSchema() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/schema.json");
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    @Before
    public void initClientMock() throws IOException, URISyntaxException {
        mock.when(HttpRequest.request()
                .withMethod("POST")
                .withHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withPath(PATH)
        ).respond(HttpResponse.response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(mockSchema()));
        client = new GraphQL(new URIBuilder().setScheme("http")
                .setHost(mock.remoteAddress().getHostString())
                .setPort(mock.remoteAddress().getPort())
                .setPath(PATH).build());
    }

    @After
    public void verifyClientInit() {
        mock.verify(HttpRequest.request().withPath(PATH), VerificationTimes.once());
    }

    @Test
    public void initializedClient() throws IOException, URISyntaxException {
        assertThat(client.getTypes(), hasItem(hasProperty("name", equalTo("Invoice"))));
        assertThat(client.getQueries(), hasItem(hasProperty("name", equalTo("invoices"))));
    }
}
