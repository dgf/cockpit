package org.aplatanao.billing.cockpit.buttons;

import javafx.scene.control.Button;
import org.aplatanao.billing.cockpit.control.Graph;
import org.aplatanao.billing.cockpit.control.Log;
import org.aplatanao.billing.cockpit.dialogs.AddAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class Add extends Button {

    @Autowired
    public Add(Log log, Graph graph) {
        setText("+ API");
        setOnAction(event -> {
            log.trace("action +");
            new AddAPI().getAPI().ifPresent(api -> {
                log.info("add " + api);
                try {
                    graph.addAPI(api);
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

}
