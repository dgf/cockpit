package org.aplatanao.billing.cockpit.models;

import javafx.beans.property.SimpleStringProperty;

public class LogEntry {
    private final SimpleStringProperty dateCol;
    private final SimpleStringProperty eventCol;
    private final SimpleStringProperty messageCol;

    public LogEntry(String dateCol, String eventCol, String messageCol) {
        this.dateCol = new SimpleStringProperty(dateCol);
        this.eventCol = new SimpleStringProperty(eventCol);
        this.messageCol = new SimpleStringProperty(messageCol);
    }

    public String getDateCol() {
        return dateCol.get();
    }

    public void setDateCol(String dateCol) {
        this.dateCol.set(dateCol);
    }

    public String getEventCol() {
        return eventCol.get();
    }

    public void setEventCol(String eventCol) {
        this.eventCol.set(eventCol);
    }

    public String getMessageCol() {
        return messageCol.get();
    }

    public void setMessageCol(String messageCol) {
        this.messageCol.set(messageCol);
    }

}