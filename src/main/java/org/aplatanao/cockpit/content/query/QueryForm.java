package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.TextInput;
import org.aplatanao.graphql.Argument;
import org.aplatanao.graphql.Field;

import java.util.Comparator;

public class QueryForm extends Form {

    public QueryForm(Field field) {
        Form.Section section = new Form.Section();
        section.setHeading("Arguments");
        getSections().add(section);

        field.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .forEach(a -> {
                    TextInput input = new TextInput();
                    Form.setLabel(input, a.getName());
                    section.add(input);
                });
    }
}
