package org.aplatanao.cockpit.status;

import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.Label;

public class CockpitStatus extends FillPane {

    public CockpitStatus() {
        setStyleName("status");
        
        add(new Label("mode"));
        add(new Label("progress"));
        add(new Label("status"));
    }
}
