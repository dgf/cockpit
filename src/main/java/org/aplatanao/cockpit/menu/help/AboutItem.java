package org.aplatanao.cockpit.menu.help;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Menu;

public class AboutItem extends Menu.Item {

    public AboutItem() {
        setButtonData("About");
        setAction(new AboutAction());
    }
}
