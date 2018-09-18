package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.*;

import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RequestField extends BoxPane {

    private boolean active = false;

    private boolean once = false;

    private Type type;

    private BoxPane fields = new BoxPane(Orientation.VERTICAL);

    private BoxPane arguments = new BoxPane(Orientation.VERTICAL);

    private Client client;

    private Field field;

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
                RequestField requestField = new RequestField(client, subField);
                requestField.setName("field");
                Checkbox checkbox = new Checkbox();
                checkbox.getButtonPressListeners().add((e) -> {
                    requestField.toggle();
                });
                checkbox.setName("checkbox");
                BoxPane pane = new BoxPane(Orientation.HORIZONTAL);
                pane.add(checkbox);
                pane.add(requestField);
                fields.add(pane);
            }
        }
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
            BoxPane box = (BoxPane) fieldComponent;
            Checkbox checkbox = (Checkbox) box.getNamedComponent("checkbox");
            RequestField field = (RequestField) box.getNamedComponent("field");
            if (selectedOnly) {
                if  (checkbox.isSelected()) {
                    box.setVisible(true);
                    checkbox.setEnabled(false);
                    field.recursiveSelectedOnly(selectedOnly);
                } else {
                    box.setVisible(false);
                }
            } else {
                if (!box.isVisible()) {
                    box.setVisible(true);
                } else {
                    checkbox.setEnabled(true);
                    field.recursiveSelectedOnly(selectedOnly);
                }
            }
        }
    }

    // recursive query build
    public String toString() {
        StringBuilder builder = new StringBuilder();
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
            BoxPane box = (BoxPane) fieldComponent;
            Checkbox checkbox = (Checkbox) box.getNamedComponent("checkbox");
            RequestField field = (RequestField) box.getNamedComponent("field");
            if (checkbox.isSelected()) { // recursive
                fieldBuilder.add(field.toString());
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
