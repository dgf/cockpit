package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;
import org.aplatanao.dimpl.Context;
import org.aplatanao.graphql.Type;

public class QueryContent extends TablePane {

    public QueryContent(Context context, QueryPreview preview, Type type) {
        setStyleName("query");

        TablePane.Column stretch = new TablePane.Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        if ("OBJECT".equals(type.getKind())) {
            FieldTree fields = context.get(FieldTree.class);
            TypeTree types = context.get(TypeTree.class);

            ScrollPane typeScroll = new ScrollPane();
            typeScroll.setView(types);
            Border typeBorder = new Border();
            typeBorder.setContent(typeScroll);

            ScrollPane fieldScroll = new ScrollPane();
            fieldScroll.setView(fields);
            Border fieldBorder = new Border();
            fieldBorder.setContent(fieldScroll);

            SplitPane main = new SplitPane();
            main.setOrientation(Orientation.HORIZONTAL);
            main.setSplitRatio(0.37f);
            main.setLeft(typeBorder);
            main.setRight(fieldBorder);

            Row mainRow = new Row();
            mainRow.setHeight("1*");
            getRows().add(mainRow);
            mainRow.add(main);

            Row previewRow = new Row();
            previewRow.setHeight(-1);
            getRows().add(previewRow);
            previewRow.add(preview);
        }
    }
}