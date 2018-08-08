package org.aplatanao.billing.cockpit.buttons;

import javafx.scene.control.Button;
import org.aplatanao.billing.cockpit.control.Graph;
import org.aplatanao.billing.cockpit.control.Log;
import org.aplatanao.billing.cockpit.dialogs.AddAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Add extends Button {

    @Autowired
    public Add(Log log, Graph graph) {
        setText("+ API");
        setOnAction(e -> {
            log.trace("action +");
            new AddAPI().getAPI().ifPresent(api -> {
                log.info("add " + api);
                graph.addAPI(api);
            });
        });
    }
}
