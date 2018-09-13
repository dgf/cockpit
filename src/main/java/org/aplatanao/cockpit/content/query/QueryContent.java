package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.dimpl.Context;
import org.aplatanao.graphql.Type;

public class QueryContent extends TablePane {

    public QueryContent(Context context, Type type) {
        setStyleName("query");

        TablePane.Column stretch = new TablePane.Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        if ("OBJECT".equals(type.getKind())) {
            FieldTree fields = context.get(FieldTree.class);

            ScrollPane fieldScroll = new ScrollPane();
            fieldScroll.setView(fields);

            Row mainRow = new Row();
            mainRow.setHeight("1*");
            getRows().add(mainRow);
            mainRow.add(fieldScroll);

            Row previewRow = new Row();
            previewRow.setHeight(-1);
            getRows().add(previewRow);
            previewRow.add(context.get(QueryPreview.class));
        }
    }
}