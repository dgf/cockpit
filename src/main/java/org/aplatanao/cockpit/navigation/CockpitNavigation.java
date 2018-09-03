package org.aplatanao.cockpit.navigation;

import org.apache.pivot.wtk.FillPane;
import org.aplatanao.cockpit.crumb.CockpitCrumbs;

public class CockpitNavigation extends FillPane {

    public CockpitNavigation(CockpitCrumbs crumbs, CockpitActions actions) {
        setStyleName("navigation");
        add(crumbs);
        add(actions);
    }
}
