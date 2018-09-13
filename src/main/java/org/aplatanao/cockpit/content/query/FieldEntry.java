package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public abstract class FieldEntry extends BoxPane {

    protected FieldType fieldType;

    protected Client client;

    protected Form.Section argumentSection = new Form.Section();

    protected BoxPane fieldsBox = new BoxPane(Orientation.VERTICAL);

    protected List<FieldEntry> fields = new ArrayList<>();

    protected Form argumentsForm = new Form();

    protected List<Argument> arguments;

    public FieldEntry(Client client, Field field) {
        this.client = client;
        this.fieldType = getType(field);

        setOrientation(Orientation.VERTICAL);

        this.arguments = field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .collect(Collectors.toList());

        BoxPane fieldHeader = new BoxPane(Orientation.HORIZONTAL);
        add(fieldHeader);
        fieldHeader.add(getHeaderPrefix());

        Label titleLabel = new Label(field.getName());
        titleLabel.setStyleName("field-title");
        fieldHeader.add(titleLabel);

        String description = field.getDescription();
        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label(description);
            descLabel.setStyleName("field-description");
            fieldHeader.add(descLabel);
        }
        fieldHeader.add(getHeaderSuffix());

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

        add(fieldsBox);
        fieldsBox.setVisible(false);
    }

    protected abstract Component getHeaderPrefix();

    protected abstract Component getHeaderSuffix();

    protected abstract boolean isFieldEnabled();

    protected void loadSubFieldsOnce(Client client) {
        if (fieldType.hasSubFields() && fieldsBox.getLength() == 0) {
            for (Field subField : fieldType.getType().getFields().stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList())) {
                FieldBranch component = new FieldBranch(client, subField);
                fieldsBox.add(component);
                fields.add(component);
            }
        }
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
                argsBuilder.add(aName + ":" + "\"" + text + "\"");
            }
        }
        if (argsBuilder.length() > 0) {
            builder.append("(");
            builder.append(argsBuilder.toString());
            builder.append(")");
        }

        StringJoiner fieldBuilder = new StringJoiner(" ");
        for (Component component : fieldsBox) {
            FieldEntry branch = (FieldEntry) component;
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
