package org.aplatanao.cockpit.content.request;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.Orientation;
import org.aplatanao.graphql.Client;
import org.aplatanao.graphql.Field;

public class Editor extends BoxPane {

    private RequestField request;

    public Editor(Client client, Field field) {
        setStyleName("request-editor");
        setOrientation(Orientation.VERTICAL);

        request = new RequestField(client, field);
        request.setStyleName("request-root");
        request.toggle();

        Checkbox selected = new Checkbox("show only selected");
        selected.getButtonPressListeners().add((e) -> {
            request.recursiveSelectedOnly(selected.isSelected());
        });

        add(selected);
        add(request);
    }

    // enclose the query parent with curly brackets
    public String toString() {
        return "{" + request.toString() + "}";
    }

}
