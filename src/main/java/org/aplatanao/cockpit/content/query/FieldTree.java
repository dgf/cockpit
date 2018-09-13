package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Label;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

public class FieldTree extends FieldEntry {

    private Checkbox filterCheckbox;

    public FieldTree(Client client, Field field, Type type) {
        super(client, field);
        setStyleName("field-tree");
        fieldsBox.setVisible(true);
        argumentsForm.setVisible(true);
        loadSubFieldsOnce(client);
    }

    @Override
    protected Component getHeaderPrefix() {
        return new Label("Request: ");
    }

    private void recursiveToggle(FieldEntry component) {
        for (FieldEntry b : component.fields) {
            if (!b.isFieldEnabled()) {
                b.setVisible(!filterCheckbox.isSelected());
            } else {
                b.setEnabled(!filterCheckbox.isSelected());
                recursiveToggle(b);
            }
        }
    }

    @Override
    protected Component getHeaderSuffix() {
        filterCheckbox = new Checkbox();
        filterCheckbox.setButtonData("show enabled only");
        filterCheckbox.getButtonPressListeners().add((e) -> {
            // run through the tree to disable all
            recursiveToggle(this);
        });
        return filterCheckbox;
    }

    @Override
    protected boolean isFieldEnabled() {
        return true;
    }

    // enclose the query parent with curly brackets
    public String toString() {
        return "{" + super.toString() + "}";
    }
}
