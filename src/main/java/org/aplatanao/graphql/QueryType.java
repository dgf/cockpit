package org.aplatanao.graphql;

import java.util.ArrayList;
import java.util.List;

public class QueryType {

    public String name;

    public String description;

    public TypeRef type;

    public List<Argument> args = new ArrayList<>();

    public String getName() {
        return name;
    }

    public QueryType setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public QueryType setDescription(String description) {
        this.description = description;
        return this;
    }

    public TypeRef getType() {
        return type;
    }

    public QueryType setType(TypeRef type) {
        this.type = type;
        return this;
    }

    public List<Argument> getArgs() {
        return args;
    }

    public QueryType setArgs(List<Argument> args) {
        this.args = args;
        return this;
    }

    @Override
    public String toString() {
        return "QueryType{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
