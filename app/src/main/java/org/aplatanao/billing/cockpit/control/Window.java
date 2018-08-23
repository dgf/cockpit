package org.aplatanao.billing.cockpit.control;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Window extends BorderPane {

    private static final KeyCombination KEYS_NEW_API = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_ANY);

    @Autowired
    public Window(Log log, Actions actions, Graph graph, Details details) {
        setTop(actions);
        setCenter(graph);
        setRight(details);
        setBottom(log);

        setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.F5) {
                keyEvent.consume();
            }
        });

        setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.F5) {
                keyEvent.consume();
            }
        });

        addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (KEYS_NEW_API.match(event)) {
                event.consume();
            }
        });
    }
}
