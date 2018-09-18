package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.*;

import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RequestField extends BoxPane {

    private boolean active = false;

    private boolean once = false;

    private int count = 1;

    private Type type;

    private Client client;

    private Field field;

    private TextInput alias = new TextInput();

    private BoxPane fields = new BoxPane(Orientation.VERTICAL);

    private BoxPane arguments = new BoxPane(Orientation.VERTICAL);

    public RequestField(Client client, Field field) {
        this.client = client;
        this.field = field;

        setStyleName("request-field");
        setOrientation(Orientation.VERTICAL);

        type = client.getType(field.getType().getName());
        boolean list = false;
        if (type == null) {
            OfType ofType = field.getType().getOfType();
            if (ofType != null) {
                if ("LIST".equals(ofType.getKind())) {
                    if ("OBJECT".equals(ofType.getOfType().getKind())) {
                        list = true;
                        type = client.getType(ofType.getOfType().getName());
                    }
                } else {
                    type = client.getType(ofType.getName());
                }
            }
        }
        if (type == null) {
            throw new IllegalArgumentException("invalid " + field);
        }

        BoxPane header = new BoxPane(Orientation.HORIZONTAL);
        add(header);

        alias.setText(field.getName());
        header.add(alias);
        header.add(new Label(":"));

        Label titleLabel = new Label(field.getName());
        titleLabel.setStyleName("field-title");
        header.add(titleLabel);

        Label typeLabel = new Label(list ? "[" + type.getName() + "]" : "(" + type.getName() + ")");
        typeLabel.setStyleName("field-type");
        header.add(typeLabel);

        String description = field.getDescription();
        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label(description);
            descLabel.setStyleName("field-description");
            header.add(descLabel);
        }
    }

    private void loadArgumentsOnce() {
        for (Argument a : field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .collect(Collectors.toList())) {
            arguments.add(new FieldArgument(a));
        }
    }

    private void loadSubFieldsOnce() {
        if (type.getFields() != null && !type.getFields().isEmpty()) {
            for (Field subField : type.getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList())) {
                SubField sf = new SubField(client, subField);
                sf.setAction("+", e -> {
                    SubField next = new SubField(client, subField);
                    next.getRequestField().setAlias(subField.getName() + count++);
                    next.setAction("-", ne -> fields.remove(next));
                    next.setCheckedEnabled(true);
                    fields.insert(next, fields.indexOf(sf) + 1);
                });
                fields.add(sf);
            }
        }
    }

    public void setAlias(String alias) {
        this.alias.setText(alias);
    }

    public void toggle() {
        if (!active && !once) { // activate
            once = true;

            loadArgumentsOnce();
            if (arguments.getLength() > 0) {
                arguments.setVisible(false);
                add(arguments);
            }

            loadSubFieldsOnce();
            if (fields.getLength() > 0) {
                fields.setVisible(false);
                add(fields);
            }
        }
        active = !active; // toggle
        arguments.setVisible(active);
        fields.setVisible(active);
    }

    public void recursiveSelectedOnly(boolean selectedOnly) {
        for (Component fieldComponent : fields) {
            SubField subField = (SubField) fieldComponent;
            if (selectedOnly) {
                if (subField.isCheckedSelected()) {
                    subField.setCheckedEnabled(false);
                    subField.getRequestField().recursiveSelectedOnly(selectedOnly);
                } else {
                    subField.setVisible(false);
                }
            } else {
                if (!subField.isVisible()) {
                    subField.setVisible(true);
                } else {
                    subField.setCheckedEnabled(true);
                    subField.getRequestField().recursiveSelectedOnly(selectedOnly);
                }
            }
        }
    }

    // recursive query build
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (!field.getName().equals(alias.getText())) {
            builder.append(alias.getText());
            builder.append(":");
        }
        builder.append(field.getName());

        StringJoiner argsBuilder = new StringJoiner(",");
        for (Component component : arguments) {
            FieldArgument argument = (FieldArgument) component;
            String text = argument.getText();
            if (!text.isEmpty()) {
                argsBuilder.add(argument.getArgumentName() + ":" + "\"" + text + "\"");
            }
        }
        if (argsBuilder.length() > 0) {
            builder.append("(");
            builder.append(argsBuilder.toString());
            builder.append(")");
        }

        StringJoiner fieldBuilder = new StringJoiner(" ");
        for (Component fieldComponent : fields) {
            SubField subField = (SubField) fieldComponent;
            if (subField.isCheckedSelected()) { // recursive
                fieldBuilder.add(subField.getRequestField().toString());
            }
        }
        if (fieldBuilder.length() > 0) {
            builder.append("{");
            builder.append(fieldBuilder.toString());
            builder.append("}");
        }
        return builder.toString();
    }
}
