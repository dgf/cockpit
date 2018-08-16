package org.aplatanao.billing.cockpit.models;

import javafx.beans.property.SimpleStringProperty;

public class LogEntry {
    private final SimpleStringProperty dateCol;
    private final SimpleStringProperty eventCol;
    private final SimpleStringProperty messageCol;

    public LogEntry(String cDate, String cEvent, String cMsg) {
        this.dateCol = new SimpleStringProperty(cDate);
        this.eventCol = new SimpleStringProperty(cEvent);
        this.messageCol = new SimpleStringProperty(cMsg);
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