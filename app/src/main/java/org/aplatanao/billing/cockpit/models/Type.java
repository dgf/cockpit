package org.aplatanao.billing.cockpit.models;

public class Type {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Type setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
