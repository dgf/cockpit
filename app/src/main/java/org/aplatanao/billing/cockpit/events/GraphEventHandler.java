package org.aplatanao.billing.cockpit.events;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.ui.view.View;

public abstract class GraphEventHandler<E extends Event> implements EventHandler<E> {

    private View view;

    private AdjacencyListGraph graph;

    public GraphEventHandler() {
    }

    public View getView() {
        return view;
    }

    public GraphEventHandler<E> setView(View view) {
        this.view = view;
        return this;
    }

    public AdjacencyListGraph getGraph() {
        return graph;
    }

    public GraphEventHandler<E> setGraph(AdjacencyListGraph graph) {
        this.graph = graph;
        return this;
    }
}
