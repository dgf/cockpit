package org.aplatanao.cockpit.menu.api;

import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuBar;

public class APIMenu extends MenuBar.Item {

    public APIMenu() {
        setButtonData("API");
        setMenu(new Menu());

        Menu.Section section = new Menu.Section();
        section.add(new AddItem());

        getMenu().getSections().add(section);
    }
}
