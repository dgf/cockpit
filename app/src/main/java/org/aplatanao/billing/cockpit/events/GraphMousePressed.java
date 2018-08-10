package org.aplatanao.billing.cockpit.events;

import javafx.scene.input.MouseEvent;
import org.aplatanao.billing.cockpit.control.Details;
import org.aplatanao.billing.cockpit.control.Log;
import org.aplatanao.billing.cockpit.models.API;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.util.InteractiveElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class GraphMousePressed extends GraphEventHandler<MouseEvent> {

    private static final EnumSet<InteractiveElement> TYPES = EnumSet.of(InteractiveElement.NODE);

    private Log log;
    private Details details;

    @Autowired
    public GraphMousePressed(Log log, Details details) {
        this.log = log;
        this.details = details;
    }

    @Override
    public void handle(MouseEvent event) {
        log.info(event.toString());
        GraphicElement element = getView().findGraphicElementAt(TYPES, event.getX(), event.getY());
        if (element == null) {
            log.info("ignore empty click");
            return;
        }

        Node node = getGraph().getNode(element.toString());
        log.info(node.toString());

        Object type = node.getAttribute("type");
        if ("api".equals(type)) {
            API api = (API) node.getAttribute("api");
            log.info("add API details " + api);

            try {
                details.addEditor(api);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
