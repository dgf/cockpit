package org.aplatanao.billing.cockpit.events;

import javafx.scene.input.KeyEvent;
import org.aplatanao.billing.cockpit.control.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphKeyPressed extends GraphEventHandler<KeyEvent> {

    private Log log;

    @Autowired
    public GraphKeyPressed(Log log) {
        this.log = log;
    }


    @Override
    public void handle(KeyEvent event) {
        log.info(event.toString());
    }
}
