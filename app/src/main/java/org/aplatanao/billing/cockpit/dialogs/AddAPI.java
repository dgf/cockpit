package org.aplatanao.billing.cockpit.dialogs;

import com.dooapp.fxform.FXForm;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.aplatanao.billing.cockpit.models.API;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class AddAPI extends Dialog<ButtonType> {

    private FXForm<API> form;

    public AddAPI() {
        setTitle("Add API Dialog");
        setHeaderText("Add an GraphQL location to explore it.");

        try {
            form = new FXForm<>(new API()
                    .setName("Awesome API")
                    .setDescription("")
                    .setUri(new URI("http://localhost:8080")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        getDialogPane().setContent(form);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (!form.isValid()) {
                event.consume();
            }
        });
    }

    public Optional<API> getAPI() {
        if (!super.showAndWait().filter(t -> t == ButtonType.OK).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(form.getSource());
    }

}
