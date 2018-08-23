package org.aplatanao.billing.cockpit.control;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.aplatanao.billing.cockpit.clients.GraphQL;
import org.aplatanao.billing.cockpit.models.API;
import org.aplatanao.billing.cockpit.models.Query;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import java.util.List;
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
    public Graph(Log log, Details details) throws IOException, URISyntaxException {
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

        //addAPI(new API("graphloc", new URL("http://api.graphloc.com/")));
        addAPI(new API("EtMDB", new URL("https://etmdb.com/graphql")));
        addAPI(new API("gdom", new URL("http://gdom.graphene-python.org/graphql")));
        addAPI(new API("melody", new URL("https://api.melody.sh/graphql")));
    }

    @PreDestroy
    public void close() {
        viewer.close();
    }

    private Node connect(Node node, String child) {
        String nid = node.getId() + "_" + child;
        String eid = node.getId() + ">" + nid;
        Node n = graph.addNode(nid);
        n.setAttribute("label", child);
        graph.addEdge(eid, node.getId(), nid);
        return n;
    }

    public void addAPI(API api) throws URISyntaxException, IOException {
        log.trace("add node " + api);
        Node node = graph.addNode(api.getName());
        node.setAttribute("api", api);
        node.setAttribute("type", "api");
        node.setAttribute("label", api.getName());
        node.setAttribute("client", new GraphQL(api.getUrl().toURI()));
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
            API api = node.getAttribute("api", API.class);
            log.info("add API details " + api);

            try {
                details.addEditor(api);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            GraphQL client = node.getAttribute("client", GraphQL.class);
            List<Query> queries = client.getQueries();

            int count = queries.size();
            Node qns = connect(node, "queries (" + count + ")");
            qns.setAttribute("type", "queries");
            qns.setAttribute("count", count);
            qns.setAttribute("client", client);

        } else if ("queries".equals(type)) {
            Integer count = node.getAttribute("count", Integer.class);
            log.info("open queries " + count);

            GraphQL client = node.getAttribute("client", GraphQL.class);
            List<Query> queries = client.getQueries();

            for (Query q : queries) {
                Node qn = connect(node, q.getName());
                qn.setAttribute("description", q.getDescription());
                qn.setAttribute("type", "query");
                qn.setAttribute("client", client);
            }

        } else if ("query".equals(type)) {
            GraphQL client = (GraphQL) node.getAttribute("client");
            // TODO add query editor
        }
    }
}
