package org.aplatanao.graphql;

public class QueryBuilder {

    private String name;

    public QueryBuilder(String name) {
        this.name = name;
    }

    public QueryBuilder arg(String name, String value) {
        return this;
    }

}
