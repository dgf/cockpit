package org.aplatanao.billing.cockpit.models;

import com.dooapp.fxform.adapter.FormAdapter;
import com.dooapp.fxform.annotation.FormFactory;
import com.dooapp.fxform.annotation.NonVisual;
import com.dooapp.fxform.validation.Warning;
import com.dooapp.fxform.view.factory.impl.TextFieldFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.validation.constraints.NotBlank;
import java.net.URL;

public class API implements Detail {

    private StringProperty name = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }

    @NotBlank
    public String getName() {
        return name.get();
    }

    public API setName(String name) {
        nameProperty().set(name);
        return this;
    }

    @FormAdapter(URLAdapter.class)
    @FormFactory(TextFieldFactory.class)
    private ObjectProperty<URL> url = new SimpleObjectProperty<>();

    public ObjectProperty<URL> urlProperty() {
        return url;
    }

    public URL getUrl() {
        return url.get();
    }

    public API setUrl(URL url) {
        urlProperty().set(url);
        return this;
    }

    private StringProperty description = new SimpleStringProperty();

    public StringProperty descriptionProperty() {
        return description;
    }

    @NotBlank(groups = Warning.class)
    public String getDescription() {
        return description.get();
    }

    public API setDescription(String description) {
        descriptionProperty().set(description);
        return this;
    }

    @NonVisual
    private StringExpression title = Bindings.concat("API ", nameProperty());

    @Override
    public StringExpression titleProperty() {
        return title;
    }

    @Override
    public String toString() {
        return "API{" +
                "name=" + name.get() +
                ", url=" + url.get() +
                ", description=" + description.get() +
                '}';
    }
}
