package org.aplatanao.billing.cockpit.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.aplatanao.billing.cockpit.models.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Log extends VBox {

    private ToolBar toolBar = new ToolBar();
    private ComboBox levelFilter = new ComboBox();
    private TableView tableView = new TableView();

    private ObservableList<LogEntry> data = FXCollections.observableArrayList();

    @Autowired
    public Log() {

        levelFilter.getItems().addAll("TRACE", "INFO", "WARN", "ERROR");
        toolBar.getItems().addAll(levelFilter);

        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
        date.prefWidthProperty().bind(widthProperty().multiply(0.2));

        TableColumn level = new TableColumn("Level");
        level.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("level"));
        level.prefWidthProperty().bind(widthProperty().multiply(0.1));

        TableColumn message = new TableColumn("Message");
        message.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("message"));
        message.prefWidthProperty().bind(widthProperty().multiply(0.7));

        tableView.getColumns().addAll(date, level, message);
        getChildren().addAll(toolBar, tableView);
    }

    private void log(String level, String message) {
        data.add(
            new LogEntry(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), level, message)
        );
        tableView.setItems(data);
    }

    public void trace(String message) {
        log("TRACE", message);
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void warn(String message) {
        log("WARN", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }
}
