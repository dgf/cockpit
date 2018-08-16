package org.aplatanao.billing.cockpit.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.aplatanao.billing.cockpit.models.LogEntry;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Component
public class Log extends LogTable {

    private final ObservableList<LogEntry> data = FXCollections.observableArrayList();

    private void log(String level, String message) {

        data.add(
            new LogEntry(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), level, message)
        );
        setItems(data);
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
