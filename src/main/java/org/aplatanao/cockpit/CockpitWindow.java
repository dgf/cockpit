package org.aplatanao.cockpit;

import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.aplatanao.cockpit.main.CockpitMain;
import org.aplatanao.cockpit.navigation.CockpitNavigation;

public class CockpitWindow extends Window {

    private CockpitNavigation navigation;

    public CockpitWindow(CockpitLayout layout, CockpitNavigation navigation) {
        this.navigation = navigation;
        setTitle("Cockpit");
        setStyleName("window");
        setMaximized(true);
        setContent(layout);
    }

    @Override
    public void open(Display display, Window ownerArgument) {
        super.open(display, ownerArgument);
        navigation.requestFocus();
    }
}
