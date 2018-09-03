package org.aplatanao.graphql;

public class TypeRef {

    public String name;

    public String getName() {
        return name;
    }

    public TypeRef setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "TypeRef{" +
                "name='" + name + '\'' +
                '}';
    }
}
