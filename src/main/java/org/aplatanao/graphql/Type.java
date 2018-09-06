package org.aplatanao.graphql;

import java.util.ArrayList;
import java.util.List;

public class Type {

    public String name;

    public String kind;

    public String description;

    public List<Field> fields = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public Type setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Type setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Type setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                ", kind='" + kind + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
