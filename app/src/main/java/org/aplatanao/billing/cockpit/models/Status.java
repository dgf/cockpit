package org.aplatanao.billing.cockpit.models;

import com.dooapp.fxform.annotation.NonVisual;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Status {

    @NonVisual
    private ObjectProperty<LocalDateTime> stamp = new SimpleObjectProperty<>();

    @NonVisual
    private IntegerProperty code = new SimpleIntegerProperty();

    @NonVisual
    private StringProperty phrase = new SimpleStringProperty("not initialized");

    @NonVisual
    private StringProperty protocol = new SimpleStringProperty();

    private ReadOnlyStringWrapper message = new ReadOnlyStringWrapper();

    public Status() {
        message.bind(Bindings.concat(code.get(), ": ", phrase.get()));
    }

    public LocalDateTime getStamp() {
        return stamp.get();
    }

    public ObjectProperty<LocalDateTime> stampProperty() {
        return stamp;
    }

    public void setStamp(LocalDateTime stamp) {
        this.stamp.set(stamp);
    }

    public int getCode() {
        return code.get();
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public String getPhrase() {
        return phrase.get();
    }

    public StringProperty phraseProperty() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase.set(phrase);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public StringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol.set(protocol);
    }

    @Override
    public String toString() {
        return "Status{" +
                "stamp=" + stamp.get() +
                ", code=" + code.get() +
                ", protocol=" + protocol.get() +
                ", phrase=" + phrase.get() +
                '}';
    }
}
