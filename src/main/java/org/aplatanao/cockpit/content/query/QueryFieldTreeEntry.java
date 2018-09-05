package org.aplatanao.cockpit.content.query;

import org.aplatanao.graphql.Field;

public class QueryFieldTreeEntry {

    private boolean checked;

    private Field field;

    public QueryFieldTreeEntry(Field field) {
        checked = false;
        this.field = field;
    }

    public boolean isChecked() {
        return checked;
    }

    public QueryFieldTreeEntry setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public Field getField() {
        return field;
    }

    public QueryFieldTreeEntry setField(Field field) {
        this.field = field;
        return this;
    }
}
