package org.aplatanao.billing.cockpit.control;

import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Log extends TextArea {

    private void log(String level, String message) {
        appendText(String.format("%s %s: %s\n", LocalDate.now(), level, message));
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
