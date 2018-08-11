package org.aplatanao.billing.cockpit.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Log extends LogTable {

    private final ObservableList<org.aplatanao.billing.cockpit.models.Log> data = FXCollections.observableArrayList();

    private void log(String level, String message) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        data.add(new org.aplatanao.billing.cockpit.models.Log(dateFormat.format(date), level, message));
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
