package org.aplatanao.cockpit.details;

import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TabPane;

public class CockpitDetails extends TabPane {

    public CockpitDetails() {
        setStyleName("details");

        Label logs = new Label("Log Table");
        getTabs().add(logs);
        TabPane.setTabData(logs, "Logs");
    }

}
