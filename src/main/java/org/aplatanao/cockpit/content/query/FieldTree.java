package org.aplatanao.cockpit.content.query;

import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

public class FieldTree extends FieldBranch {

    public FieldTree(Client client, Field field, Type type) {
        super(client, field);
        setStyleName("field-tree");
    }


    // enclose the query parent with curly brackets
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
