package org.aplatanao.cockpit.content.query;

import org.apache.pivot.wtk.*;

public class QueryPreview extends TablePane {

    public QueryPreview() {
        setStyleName("preview");

        TextArea preview = new TextArea();
        preview.setText("foo\nbar");

        BoxPane buttons = new BoxPane(Orientation.VERTICAL);
        PushButton refreshButton = new PushButton("Refresh");
        PushButton requestButton = new PushButton("Request");
        buttons.add(refreshButton);
        buttons.add(requestButton);

        TablePane.Column stretch = new TablePane.Column();
        stretch.setWidth("1*");
        getColumns().add(stretch);

        TablePane.Column right = new TablePane.Column();
        right.setWidth(-1);
        getColumns().add(right);

        Row mainRow = new Row();
        mainRow.setHeight(-1);
        getRows().add(mainRow);
        mainRow.add(preview);
        mainRow.add(buttons);
    }
}
