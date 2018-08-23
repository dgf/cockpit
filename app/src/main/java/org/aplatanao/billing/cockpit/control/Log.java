package org.aplatanao.billing.cockpit.control;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.aplatanao.billing.cockpit.models.LogEntry;
import org.aplatanao.billing.cockpit.models.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Log extends TitledPane {

    private ObservableList<LogEntry> data = FXCollections.observableArrayList();

    @Autowired
    public Log() {
        VBox content = new VBox();
        setContent(content);
        setText("Log");
        setExpanded(false);

        TableView<LogEntry> table = new TableView<>(data);

        TableColumn<LogEntry, LocalDateTime> date = new TableColumn<>("Date");
        date.setCellValueFactory(l -> new ReadOnlyObjectWrapper<>(l.getValue().getDate()));
        date.prefWidthProperty().bind(widthProperty().multiply(0.2));
        table.getColumns().add(date);

        TableColumn<LogEntry, LogLevel> level = new TableColumn<>("Level");
        level.setCellValueFactory(l -> new ReadOnlyObjectWrapper<>(l.getValue().getLevel()));
        level.prefWidthProperty().bind(widthProperty().multiply(0.1));
        table.getColumns().add(level);

        TableColumn<LogEntry, String> message = new TableColumn<>("Message");
        message.setCellValueFactory(l -> new ReadOnlyObjectWrapper<>(l.getValue().getMessage()));
        message.prefWidthProperty().bind(widthProperty().multiply(0.7));
        table.getColumns().add(message);

        ComboBox<LogLevel> levelFilter = new ComboBox<>();
        levelFilter.getItems().addAll(LogLevel.TRACE, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR);

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(levelFilter);

        content.getChildren().addAll(toolBar, table);
    }

    private void log(LogLevel level, String message) {
        data.add(new LogEntry(LocalDateTime.now(), level, message));
    }

    public void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
}
