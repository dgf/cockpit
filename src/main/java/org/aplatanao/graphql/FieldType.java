package org.aplatanao.graphql;

public class FieldType {

    public String name;

    public OfType ofType;

    public String getName() {
        return name;
    }

    public FieldType setName(String name) {
        this.name = name;
        return this;
    }

    public OfType getOfType() {
        return ofType;
    }

    public FieldType setOfType(OfType ofType) {
        this.ofType = ofType;
        return this;
    }

    @Override
    public String toString() {
        return "FieldType{" +
                "name='" + name + '\'' +
                ", ofType=" + ofType +
                '}';
    }
}
