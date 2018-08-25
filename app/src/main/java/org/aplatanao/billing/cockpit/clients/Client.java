package org.aplatanao.billing.cockpit.clients;

import com.dooapp.fxform.annotation.NonVisual;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.aplatanao.billing.cockpit.models.Status;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public abstract class Client {

    @NonVisual
    protected ObjectMapper mapper = new ObjectMapper();

    @NonVisual
    protected CloseableHttpClient client = HttpClients.createDefault();

    protected ObjectProperty<Status> status = new SimpleObjectProperty<>(new Status());

    public abstract void init(URI endpoint) throws IOException;

    protected void updateStatus(CloseableHttpResponse response) {
        StatusLine statusLine = response.getStatusLine();
        status.get().setCode(statusLine.getStatusCode());
        status.get().setPhrase(statusLine.getReasonPhrase());
        status.get().setProtocol(statusLine.getProtocolVersion().toString());
        status.get().setStamp(LocalDateTime.now());
    }

    public Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    protected CloseableHttpResponse execute(HttpRequestBase request) throws IOException {
        return client.execute(request);
    }
}
