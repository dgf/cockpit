package org.aplatanao.billing.cockpit.dialogs;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.aplatanao.billing.cockpit.models.API;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class AddAPI extends Dialog<ButtonType> {

    private TextField name = new TextField("Awesome API");

    private TextField url = new TextField("http://localhost:8080");

    private TextField description = new TextField("");

    public AddAPI() {
        setTitle("Add API Dialog");
        setHeaderText("Add an GraphQL location to explore it.");

        GridPane grid = new GridPane();
        grid.add(new Label("Name"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("URL"), 0, 1);
        grid.add(url, 1, 1);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            boolean valid = true;

            if (name.getText().isEmpty()) {
                System.out.println("Name is empty");
                name.setStyle("-fx-border-color: red;");
                valid = false;
            }

            if (url.getText().isEmpty()) {
                System.out.println("URL is empty");
                url.setStyle("-fx-border-color: red;");
                valid = false;
            } else {
                try {
                    new URL(url.getText());
                } catch (MalformedURLException e) {
                    System.out.println("URL is invalid");
                    url.setStyle("-fx-border-color: red;");
                    valid = false;
                }
            }
            if (!valid) {
                event.consume();
            }
        });
    }

    public Optional<API> getAPI() {
        Optional<ButtonType> optional = super.showAndWait().filter(t -> t == ButtonType.OK);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new API()
                    .setName(name.getText())
                    .setUrl(new URL(url.getText()))
                    .setDescription(description.getText()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
