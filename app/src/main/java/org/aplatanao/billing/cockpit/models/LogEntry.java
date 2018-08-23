package org.aplatanao.billing.cockpit.models;

import java.time.LocalDateTime;

public class LogEntry {

    private LocalDateTime date;

    private LogLevel level;

    private String message;

    public LogEntry(LocalDateTime date, LogLevel level, String message) {
        this.date = date;
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LogEntry setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public LogLevel getLevel() {
        return level;
    }

    public LogEntry setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntry setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "date=" + date +
                ", level=" + level +
                ", message='" + message + '\'' +
                '}';
    }
}