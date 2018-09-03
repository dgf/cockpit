package org.aplatanao.cockpit.menu;

import org.apache.pivot.wtk.MenuBar;
import org.aplatanao.cockpit.menu.api.APIMenu;
import org.aplatanao.cockpit.menu.help.HelpMenu;

public class CockpitMenu extends MenuBar {

    public CockpitMenu(APIMenu apiMenu, HelpMenu helpMenu) {
        setStyleName("menu");
        setHeight(20);
        getItems().add(apiMenu);
        getItems().add(helpMenu);
    }

}
