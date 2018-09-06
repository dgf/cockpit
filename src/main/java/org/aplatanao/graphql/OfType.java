package org.aplatanao.graphql;

public class OfType {

    public String kind;

    public String name;

    public OfType ofType;

    public String getKind() {
        return kind;
    }

    public OfType setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfType setName(String name) {
        this.name = name;
        return this;
    }

    public OfType getOfType() {
        return ofType;
    }

    public OfType setOfType(OfType ofType) {
        this.ofType = ofType;
        return this;
    }

    @Override
    public String toString() {
        return "OfType{" +
                "kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", ofType=" + ofType +
                '}';
    }
}
