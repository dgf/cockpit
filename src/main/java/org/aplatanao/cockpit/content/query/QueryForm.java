package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.Argument;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.QueryType;
import org.aplatanao.graphql.Type;

import java.util.Comparator;

public class QueryForm extends BoxPane {

    public QueryForm(Client client, QueryType queryType) {
        Type type = client.getType(queryType.getType().getName());
        setOrientation(Orientation.HORIZONTAL);

        if ("OBJECT".equals(type.getKind())) {
            Border treeBorder = new Border(new QueryFieldTree(client, type));
            treeBorder.setTitle("Fields");
            add(treeBorder);
        }

        Border formBorder = new Border(loadForm(queryType));
        formBorder.setTitle("Parameters");
        add(formBorder);
        add(new QueryPreview());
    }

    private Form loadForm(QueryType queryType) {
        Form form = new Form();

        Form.Section section = new Form.Section();
        section.setHeading(queryType.getName());
        form.getSections().add(section);

        queryType.getArgs().stream()
                .sorted(Comparator.comparing(Argument::getName))
                .forEach(a -> {
                    TextInput input = new TextInput();
                    Form.setLabel(input, a.getName());
                    section.add(input);
                });

        form.load(queryType);
        return form;
    }
}