package org.aplatanao.billing.cockpit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

public class CockpitController {

    @FXML
    private TextArea response;

    @FXML
    private Tab tabGraph;

    @FXML
    private void initialize() {
        Graph graph = new SingleGraph("Tutorial 1");

        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addEdge( "AB", "A", "B" );
        graph.addEdge( "BC", "B", "C" );
        graph.addEdge( "CA", "C", "A" );

        SpriteManager sman = new SpriteManager( graph );





        Viewer viewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
        viewer.enableAutoLayout();
        FxDefaultView view = (FxDefaultView) viewer.addDefaultView(false);

        view.resize(tabGraph.getTabPane().getWidth(), tabGraph.getTabPane().getHeight());
        tabGraph.setContent(view);
    }

    @FXML
    private void connect(ActionEvent event) {
        response.setText("Hola mundo!");
    }
}
