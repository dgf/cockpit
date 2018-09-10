package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.Argument;
import org.aplatanao.graphql.Field;

import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class FieldBranch extends BoxPane {

    private Field field;

    private Form.Section arguments = new Form.Section();

    private BoxPane fields = new BoxPane(Orientation.VERTICAL);

    public FieldBranch(Field field) {
        this.field = field;
        setOrientation(Orientation.VERTICAL);
        setStyleName("field-branch");
        setVisible(false);

        String title = field.getName();
        String description = field.getDescription();
        if (description != null && !description.isEmpty()) {
            title += ": " + description;
        }

        add(new Label(title));
        addArguments(field);
        add(fields);
    }

    public FieldBranch addField(Field field) {
        FieldBranch branch = new FieldBranch(field);
        fields.add(branch);
        return branch;
    }

    private void addArguments(Field field) {
        Form form = new Form();
        form.getSections().add(arguments);
        for (Argument a : field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .collect(Collectors.toList())) {
            TextInput input = new TextInput();
            Form.setLabel(input, a.getName());
            arguments.add(input);
        }
        add(form);
    }

    // recursive query build
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(field.getName());

        StringJoiner argsBuilder = new StringJoiner(", ");
        for (Component component : arguments) {
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
            if (branch.isVisible()) { // recursive
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
