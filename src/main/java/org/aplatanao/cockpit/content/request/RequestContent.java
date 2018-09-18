package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TablePane;

public class RequestContent extends TablePane {

    public RequestContent(Editor editor, Preview preview) {
        setStyleName("request-content");

        Column stretch = new Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        ScrollPane scroll = new ScrollPane();
        scroll.setView(editor);

        Row mainRow = new Row();
        getRows().add(mainRow);
        mainRow.setHeight("1*");
        mainRow.add(scroll);

        Row previewRow = new Row();
        getRows().add(previewRow);
        previewRow.setHeight(-1);
        previewRow.add(preview);
    }
}