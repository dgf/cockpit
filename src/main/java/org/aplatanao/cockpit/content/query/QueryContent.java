package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.ScrollPane;
import org.aplatanao.dimpl.Context;
import org.aplatanao.graphql.Type;

public class QueryContent extends ScrollPane {

    public QueryContent(Context context, Type type) {
        setStyleName("query");

        BoxPane main = new BoxPane();
        main.setOrientation(Orientation.HORIZONTAL);
        setView(main);

        if ("OBJECT".equals(type.getKind())) {
            FieldTree fields = context.get(FieldTree.class);
            TypeTree types = context.get(TypeTree.class);

            main.add(types);
            main.add(fields);
        }
    }
}