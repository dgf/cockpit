package org.aplatanao.cockpit.graphql;

import org.aplatanao.cockpit.graphql.Client;

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

    @Override
    public String toString() {
        return "API{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
