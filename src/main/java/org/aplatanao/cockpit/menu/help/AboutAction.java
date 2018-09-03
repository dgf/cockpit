package org.aplatanao.cockpit.menu.help;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Component;

public class AboutAction extends Action {

    @Override
    public void perform(Component source) {
        System.out.println("About");
    }
}
