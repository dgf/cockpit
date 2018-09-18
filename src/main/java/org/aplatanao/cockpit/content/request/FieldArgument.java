package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TextInput;
import org.aplatanao.graphql.Argument;

public class FieldArgument extends BoxPane {

    private Argument argument;

    private TextInput input = new TextInput();

    public FieldArgument(Argument argument) {
        this.argument = argument;

        Label nameLabel = new Label(argument.getName());
        nameLabel.setStyleName("argument-title");
        add(nameLabel);

        String typeName = argument.getType().getName();
        if (typeName != null) {
            Label typeLabel = new Label("(" + typeName + ")");
            typeLabel.setStyleName("argument-type");
            add(typeLabel);
        }

        add(input);
        if (argument.getDefaultValue() != null) {
            input.setText(String.valueOf(argument.getDefaultValue()));
        }

        String description = argument.getDescription();
        if (description != null) {
            Label descLabel = new Label(description);
            descLabel.setStyleName("argument-description");
            add(descLabel);
        }
    }

    public String getText() {
        return input.getText();
    }

    public String getArgumentName() {
        return argument.getName();
    }
}
