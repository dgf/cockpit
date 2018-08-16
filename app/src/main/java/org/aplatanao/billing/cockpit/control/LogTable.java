package org.aplatanao.billing.cockpit.control;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.aplatanao.billing.cockpit.models.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogTable extends TableView {

    @Autowired
    public LogTable() {

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("dateCol"));
        dateCol.prefWidthProperty().bind(widthProperty().multiply(0.2));

        TableColumn eventCol = new TableColumn("Event");
        eventCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("eventCol"));
        eventCol.prefWidthProperty().bind(widthProperty().multiply(0.1));

        TableColumn messageCol = new TableColumn("Message");
        messageCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("messageCol"));
        messageCol.prefWidthProperty().bind(widthProperty().multiply(0.7));

        getColumns().addAll(dateCol, eventCol, messageCol);

    }

}
