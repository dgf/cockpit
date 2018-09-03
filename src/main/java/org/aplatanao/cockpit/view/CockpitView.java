package org.aplatanao.cockpit.view;

import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TabPane;

public class CockpitView extends TabPane {

    public CockpitView() {
        setStyleName("view");

        Label logs = new Label("Log Table");
        getTabs().add(logs);
        TabPane.setTabData(logs, "Logs");

        Label results = new Label("Results");
        getTabs().add(results);
        TabPane.setTabData(results, "Query Results");
    }

}
