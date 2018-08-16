package org.aplatanao.billing.cockpit.control;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.aplatanao.billing.cockpit.models.API;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.util.InteractiveElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Component
public class Graph extends AnchorPane implements EventHandler<MouseEvent> {

    private Log log;

    private Details details;

    private MultiGraph graph;

    private FxViewer viewer;

    private FxDefaultView view;

    private String getStyleSheet() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/graph.css");
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    @Autowired
    public Graph(Log log, Details details) throws IOException {
        this.log = log;
        this.details = details;

        graph = new MultiGraph("explorer");
        viewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        graph.setAttribute("ui.stylesheet", getStyleSheet());
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.quality");

        view = (FxDefaultView) viewer.addView("main", new FxGraphRenderer());
        view.addListener(MouseEvent.MOUSE_PRESSED, this);
        getChildren().add(view);

        // anchor the view for dynamic resizing
        setTopAnchor(view, 0.0);
        setBottomAnchor(view, 0.0);
        setLeftAnchor(view, 0.0);
        setRightAnchor(view, 0.0);

        //addAPI(new API("local", new URL("http://localhost:8080")));
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
        node.setAttribute("label", api.getName());
    }

    private void connect(Node node, String child) {
        String nid = node.getId() + "_" + child;
        String eid = node.getId() + ">" + nid;
        graph.addNode(nid).setAttribute("label", child);
        graph.addEdge(eid, node.getId(), nid);
    }

    @Override
    public void handle(MouseEvent event) {
        log.trace(event.toString());
        EnumSet<InteractiveElement> types = EnumSet.of(InteractiveElement.NODE);

        GraphicElement element = view.findGraphicElementAt(types, event.getX(), event.getY());
        if (element == null) {
            log.trace("ignore click on empty space");
            return;
        }

        Node node = graph.getNode(element.toString());
        Object type = node.getAttribute("type");
        if ("api".equals(type)) {
            API api = (API) node.getAttribute("api");
            log.info("add API details " + api);

            try {
                details.addEditor(api);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            connect(node, "types");
            connect(node, "queries");
            connect(node, "mutations");
        }
    }
}
