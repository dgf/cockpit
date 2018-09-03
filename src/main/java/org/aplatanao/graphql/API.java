package org.aplatanao.graphql;

public class API {

    public String name;

    public String uri;

    public String description;

    public Client client;

    public API(String name, String uri, String description) {
        this.name = name;
        this.uri = uri;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public API setName(String name) {
        this.name = name;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public API setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public API setDescription(String description) {
        this.description = description;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public API setClient(Client client) {
        this.client = client;
        return this;
    }

    @Override
    public String toString() {
        return "API{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
