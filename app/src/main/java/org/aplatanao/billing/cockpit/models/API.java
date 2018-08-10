package org.aplatanao.billing.cockpit.models;

import java.net.URL;

public class API extends Data {

    private String name;

    private URL url;

    public API(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public API setName(String name) {
        this.name = name;
        return this;
    }

    public URL getUrl() {
        return url;
    }

    public API setUrl(URL url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "API{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }

    @Override
    public String getTitle() {
        return "API " + name;
    }
}
