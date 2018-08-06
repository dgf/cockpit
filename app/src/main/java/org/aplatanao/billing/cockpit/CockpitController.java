package org.aplatanao.billing.cockpit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CockpitController {

    @FXML
    private TextArea response;

    @FXML
    private void initialize() {
    }

    @FXML
    private void connect(ActionEvent event) {
        response.setText("Hola mundo!");
    }
}
