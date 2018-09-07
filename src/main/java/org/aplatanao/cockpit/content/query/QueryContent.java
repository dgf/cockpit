package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TablePane;
import org.aplatanao.context.Context;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;
import org.aplatanao.graphql.Type;

public class QueryContent extends TablePane {

    public QueryContent(Client client, Field field, Context context) {
        String typeName = field.getType().getName();
        Type type = client.getType(typeName);
        setStyleName("query");

        Column column = new Column();
        column.setWidth("1*");
        getColumns().add(column);

        BoxPane main = new BoxPane();
        main.setOrientation(Orientation.HORIZONTAL);

        QueryForm form = context.get(QueryForm.class);
        if ("OBJECT".equals(type.getKind())) {
            main.add(context.get(QueryTree.class));
        }
        main.add(form);

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

}