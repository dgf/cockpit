package org.aplatanao.billing.cockpit.control;

import javafx.scene.control.ToolBar;
import org.aplatanao.billing.cockpit.buttons.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Actions extends ToolBar {

    @Autowired
    public Actions(Add add) {
        getItems().add(add);
    }
}
