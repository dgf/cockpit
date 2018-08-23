package org.aplatanao.billing.cockpit.models;

public class Query {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public Query setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Query setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Query{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
