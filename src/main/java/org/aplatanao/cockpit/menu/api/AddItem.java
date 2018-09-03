package org.aplatanao.cockpit.menu.api;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Menu;

public class AddItem extends Menu.Item {

    public AddItem() {
        setButtonData("Add");
        setAction(new AddAction());
    }
}
