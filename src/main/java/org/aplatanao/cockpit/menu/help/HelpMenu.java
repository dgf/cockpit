package org.aplatanao.cockpit.menu.help;

import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuBar;

public class HelpMenu extends MenuBar.Item {

    public HelpMenu() {
        setButtonData("Help");

        Menu menu = new Menu();

        Menu.Section section = new Menu.Section();
        section.add(new AboutItem());
        menu.getSections().add(section);

        setMenu(menu);
    }
}
