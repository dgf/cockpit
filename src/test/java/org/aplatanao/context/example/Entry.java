package org.aplatanao.context.example;

public class Entry {

    private String key;

    private String value;

    private Storage storage;

    public Entry(Storage storage) {
        this.storage = storage;
    }

    public Entry save() {
        storage.put(key, this);
        return this;
    }

    public String getKey() {
        return key;
    }

    public Entry setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Entry setValue(String value) {
        this.value = value;
        return this;
    }

    public Storage getStorage() {
        return storage;
    }

    public Entry setStorage(Storage storage) {
        this.storage = storage;
        return this;
    }
}
