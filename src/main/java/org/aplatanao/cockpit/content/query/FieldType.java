package org.aplatanao.cockpit.content.query;

import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

public class FieldType {

    private boolean list;

    private Field field;

    private Type type;

    public String getName() {
        if (list) {
            return field.getName() + " [" + type.getName() + "]";
        }
        return field.getName() + " (" + type.getName() + ")";
    }

    public boolean isList() {
        return list;
    }

    public boolean hasSubFields() {
        return type.getFields() != null && !type.getFields().isEmpty();
    }

    public FieldType setList(boolean list) {
        this.list = list;
        return this;
    }

    public Field getField() {
        return field;
    }

    public FieldType setField(Field field) {
        this.field = field;
        return this;
    }

    public Type getType() {
        return type;
    }

    public FieldType setType(Type type) {
        this.type = type;
        return this;
    }
}
