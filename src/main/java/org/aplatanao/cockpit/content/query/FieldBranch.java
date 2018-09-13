package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.*;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class FieldBranch extends BoxPane {

    private FieldType fieldType;

    private Client client;

    private Form.Section argumentSection = new Form.Section();

    private BoxPane fields = new BoxPane(Orientation.VERTICAL);

    private Form argumentsForm = new Form();

    private Checkbox fieldCheckbox = new Checkbox();

    public FieldBranch(Client client, Field field) {
        this.client = client;
        this.fieldType = getType(field);

        setOrientation(Orientation.VERTICAL);
        setStyleName("field-branch");

        List<Argument> arguments = field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .collect(Collectors.toList());

        BoxPane fieldHeader = new BoxPane(Orientation.HORIZONTAL);
        add(fieldHeader);

        fieldHeader.add(fieldCheckbox);
        fieldCheckbox.getButtonPressListeners().add((e) -> {
            if (!arguments.isEmpty()) {
                argumentsForm.setVisible(!argumentsForm.isVisible());
            }
            if (fields.getLength() > 0) {
                fields.setVisible(!fields.isVisible());
            }
            if (fieldType.hasSubFields() && fields.getLength() == 0) {
                for (Field subField : fieldType.getType().getFields().stream()
                        .sorted(Comparator.comparing(Field::getName))
                        .collect(Collectors.toList())) {
                    fields.add(new FieldBranch(client, subField));
                }
            }
        });

        Label titleLabel = new Label(field.getName());
        titleLabel.setStyleName("field-title");
        fieldHeader.add(titleLabel);

        String description = field.getDescription();
        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label(description);
            descLabel.setStyleName("field-description");
            fieldHeader.add(descLabel);
        }

        if (!arguments.isEmpty()) {
            add(argumentsForm);
            argumentsForm.setVisible(false);
            argumentsForm.getSections().add(argumentSection);
            for (Argument a : arguments) {
                TextInput input = new TextInput();
                Form.setLabel(input, a.getName());
                argumentSection.add(input);
            }
        }

        add(fields);
    }

    public boolean isFieldEnabled() {
        return fieldCheckbox.isSelected();
    }

    private FieldType getType(Field field) {
        boolean isList = false;

        Type type = client.getType(field.getType().getName());
        if (type == null) {
            OfType ofType = field.getType().getOfType();
            if (ofType != null) {
                if ("LIST".equals(ofType.getKind())) {
                    if ("OBJECT".equals(ofType.getOfType().getKind())) {
                        isList = true;
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
        return new FieldType().setList(isList).setType(type).setField(field);
    }

    // recursive query build
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(fieldType.getField().getName());

        StringJoiner argsBuilder = new StringJoiner(", ");
        for (Component component : argumentSection) {
            TextInput input = (TextInput) component;
            String text = input.getText();
            if (!text.isEmpty()) {
                String aName = Form.getLabel(component);
                argsBuilder.add(aName + ": " + "\"" + text + "\"");
            }
        }
        if (argsBuilder.length() > 0) {
            builder.append("(");
            builder.append(argsBuilder.toString());
            builder.append(")");
        }

        StringJoiner fieldBuilder = new StringJoiner(" ");
        for (Component component : fields) {
            FieldBranch branch = (FieldBranch) component;
            if (branch.isFieldEnabled()) { // recursive
                fieldBuilder.add(branch.toString());
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
