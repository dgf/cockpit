package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.graphql.Argument;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.QueryType;
import org.aplatanao.graphql.Type;

import java.util.Comparator;

public class QueryContent extends TablePane {

    public QueryContent(Client client, QueryType queryType) {
        setStyleName("query");

        Column column = new Column();
        column.setWidth("1*");
        getColumns().add(column);

        BoxPane main = new BoxPane();
        main.setOrientation(Orientation.HORIZONTAL);

        Type type = client.getType(queryType.getType().getName());
        if ("OBJECT".equals(type.getKind())) {
            main.add(new QueryFieldTree(client, type));
        }
        main.add(loadForm(queryType));

        ScrollPane mainScroll = new ScrollPane();
        mainScroll.setView(main);
        Row mainRow = new Row();
        mainRow.setHeight("1*");
        mainRow.add(mainScroll);
        getRows().add(mainRow);

        Row previewRow = new Row();
        previewRow.setHeight(-1);
        previewRow.add(new QueryPreview());
        getRows().add(previewRow);
    }

    private Form loadForm(QueryType queryType) {
        Form form = new Form();

        Form.Section section = new Form.Section();
        section.setHeading("Arguments");
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