package org.aplatanao.cockpit.controls;

import org.apache.pivot.wtk.FillPane;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;

public class CockpitControls extends FillPane {

    public CockpitControls(CockpitCrumbs crumbs, CockpitActions actions) {
        setStyleName("controls");
        add(crumbs);
        add(actions);
    }
}
