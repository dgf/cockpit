package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Label;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;

public class FieldBranch extends FieldEntry {

    private Checkbox fieldCheckbox;

    public FieldBranch(Client client, Field field) {
        super(client, field);
        setStyleName("field-branch");
    }

    @Override
    protected Component getHeaderPrefix() {
        fieldCheckbox = new Checkbox();
        fieldCheckbox.getButtonPressListeners().add((e) -> {
            if (!arguments.isEmpty()) {
                argumentsForm.setVisible(!argumentsForm.isVisible());
            }
            loadSubFieldsOnce(client);
            if (fieldsBox.getLength() > 0) {
                fieldsBox.setVisible(!fieldsBox.isVisible());
            }
        });
        return fieldCheckbox;
    }

    public void setEnabled(boolean enabled) {
        fieldCheckbox.setEnabled(enabled);
    }

    @Override
    protected Component getHeaderSuffix() {
        return new Label("branch suffix");
    }

    public boolean isFieldEnabled() {
        return fieldCheckbox.isSelected();
    }

}
