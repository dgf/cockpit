package org.aplatanao.billing.cockpit.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
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

        //center
        setAlignment(graph, Pos.TOP_LEFT);
        setMargin(graph, new Insets(0,0,0,0));
        setCenter(graph);

        setRight(details);
        setBottom(new TitledPane("Log", log));

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
