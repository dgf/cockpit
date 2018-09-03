package org.aplatanao.cockpit.menu.api;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Component;

public class AddAction extends Action {

    @Override
    public void perform(Component source) {
        System.out.println("add API");
    }
}
