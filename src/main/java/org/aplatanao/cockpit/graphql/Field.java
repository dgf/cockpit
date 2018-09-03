package org.aplatanao.cockpit.graphql;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public String name;

    public String description;

    public TypeRef type;

    public List<Argument> args = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Field setDescription(String description) {
        this.description = description;
        return this;
    }

    public TypeRef getType() {
        return type;
    }

    public Field setType(TypeRef type) {
        this.type = type;
        return this;
    }

    public List<Argument> getArgs() {
        return args;
    }

    public Field setArgs(List<Argument> args) {
        this.args = args;
        return this;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
