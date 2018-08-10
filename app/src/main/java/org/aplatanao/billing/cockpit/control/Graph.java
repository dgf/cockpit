package org.aplatanao.billing.cockpit.control;

import javafx.scene.layout.Pane;
import org.aplatanao.billing.cockpit.events.GraphEvents;
import org.aplatanao.billing.cockpit.models.API;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class Graph extends Pane {

    private static AdjacencyListGraph graph;

    private FxViewer viewer;

    private Log log;

    private String getStyleSheet() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/graph.css");
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    @Autowired
    public Graph(Log log, GraphEvents events) throws IOException {
        this.log = log;

        graph = new MultiGraph("explorer");
        viewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        graph.setAttribute("ui.stylesheet", getStyleSheet());
        viewer.enableAutoLayout();

        View panel = viewer.addDefaultView(false, new FxGraphRenderer());
        events.register(panel, graph);
        getChildren().add((javafx.scene.Node) panel);

        addAPI(new API("local", new URL("http://localhost:8080")));
        addAPI(new API("github", new URL("https://api.github.com/graphql")));
        addAPI(new API("swapi", new URL("https://graphql.org/swapi-graphql/")));
    }

    @PreDestroy
    public void close() {
        viewer.close();
    }

    public void addAPI(API api) {
        log.trace("add node " + api);
        Node node = graph.addNode(api.getName());
        node.setAttribute("api", api);
        node.setAttribute("type", "api");
        node.setAttribute("label", api.getName() + " " + api.getUrl());
    }

}
