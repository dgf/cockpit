package org.aplatanao.cockpit.controls;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.PushButton;

public class CockpitActions extends BoxPane {

    public CockpitActions() {
        setStyleName("actions");
        add(new PushButton("start"));
        add(new PushButton("pause"));
        add(new PushButton("stop"));
    }
}
