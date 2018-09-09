package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.Argument;
import org.aplatanao.graphql.Field;

import java.util.Comparator;
import java.util.stream.Collectors;

public class FieldBranch extends BoxPane {

    private Form arguments = new Form();

    private BoxPane fields = new BoxPane(Orientation.VERTICAL);

    public FieldBranch(Field field) {
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
        Form.Section section = new Form.Section();
        arguments.getSections().add(section);
        for (Argument a : field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .collect(Collectors.toList())) {
            TextInput input = new TextInput();
            Form.setLabel(input, a.getName());
            section.add(input);
        }
        add(arguments);
    }
}
