package org.aplatanao.billing.cockpit.control;

import javafx.scene.layout.Pane;
import org.aplatanao.billing.cockpit.models.API;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class Graph extends Pane {

    private static AdjacencyListGraph graph;

    private FxViewer viewer;

    private Log log;

    @Autowired
    public Graph(Log log) throws MalformedURLException {
        this.log = log;

        graph = new MultiGraph("explorer");

        viewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        getChildren().add((javafx.scene.Node) viewer.addDefaultView(false, new FxGraphRenderer()));

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
        node.setAttribute("label", api.getName() + " " + api.getUrl());
    }
}
