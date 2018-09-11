package org.aplatanao.cockpit.details;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TextArea;

import java.io.IOException;
import java.io.StringWriter;

public class CockpitDetails extends TabPane {

    private int count = 0;

    private ObjectMapper mapper = new ObjectMapper();

    public CockpitDetails() {
        setStyleName("details");
        setCloseable(true);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Label logs = new Label("Log Table");
        getTabs().add(logs);
        TabPane.setTabData(logs, "Logs");
    }

    public void response(ObjectNode node) throws IOException {
        StringWriter w = new StringWriter();
        mapper.writeValue(w, node);

        TextArea response = new TextArea();
        response.setText(w.toString());

        ScrollPane scroll = new ScrollPane();
        scroll.setView(response);

        getTabs().add(scroll);
        count++;
        TabPane.setTabData(response, "Response (" + count + ")");
        setSelectedTab(scroll);
    }

}
