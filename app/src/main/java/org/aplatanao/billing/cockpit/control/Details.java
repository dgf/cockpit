package org.aplatanao.billing.cockpit.control;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import org.aplatanao.billing.cockpit.models.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Details extends Accordion {

    private Log log;

    @Autowired
    public Details(Log log) {
        this.log = log;
    }

    private void addPane(String title, Node content) {
        getPanes().add(new TitledPane(title, content));
    }

    public void addEditor(Data data) throws IllegalAccessException {
        String title = data.getTitle();
        log.info("add editor " + title);

        GridPane pane = new GridPane();
        int row = 0;
        Field[] fields = data.getClass().getDeclaredFields();
        log.info("fields " + fields.length);

        for (Field f : fields) {
            f.setAccessible(true);
            Label label = new Label(f.getName());
            TextField field = new TextField(f.get(data).toString());

            GridPane.setConstraints(label, 0, row); // column=2 row=0
            GridPane.setConstraints(field, 1, row); // column=2 row=0

            pane.getChildren().addAll(label, field);
            row++;
        }
        addPane(title, pane);
    }

}
