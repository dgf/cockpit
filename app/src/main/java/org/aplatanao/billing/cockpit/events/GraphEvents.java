package org.aplatanao.billing.cockpit.events;

import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.ui.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GraphEvents extends HashMap<EventType<? extends InputEvent>, GraphEventHandler> {

    @Autowired
    public GraphEvents(GraphKeyPressed keyPressed, GraphMousePressed mousePressed) {
        put(KeyEvent.KEY_PRESSED, keyPressed);
        put(MouseEvent.MOUSE_PRESSED, mousePressed);
    }

    public void register(View view, AdjacencyListGraph graph) {
        forEach((type, handler) -> {
            view.addListener(type, handler);
            handler.setView(view);
            handler.setGraph(graph);
        });
    }
}
