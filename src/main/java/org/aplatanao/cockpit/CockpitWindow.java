package org.aplatanao.cockpit;

import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.aplatanao.cockpit.main.CockpitMain;

public class CockpitWindow extends Window {

    private CockpitMain main;

    public CockpitWindow(CockpitLayout layout, CockpitMain main) {
        this.main = main;
        setTitle("Cockpit");
        setStyleName("window");
        setMaximized(true);
        setContent(layout);
    }

    @Override
    public void open(Display display, Window ownerArgument) {
        super.open(display, ownerArgument);
        main.requestFocus();
    }
}
