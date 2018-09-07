package org.aplatanao.context.example;

import org.aplatanao.context.Context;

public class Entry {

    private String key;

    private String value;

    private Storage storage;

    private StringReverser reverser;

    private Context context;

    public Entry(Storage storage, StringReverser reverser, Context context) {
        this.storage = storage;
        this.reverser = reverser;
        this.context = context;
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

    public String getReverseValue() {
        return reverser.reverse(value);
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

    public StringReverser getReverser() {
        return reverser;
    }

    public Entry setReverser(StringReverser reverser) {
        this.reverser = reverser;
        return this;
    }


    public Context getContext() {
        return context;
    }

    public Entry setContext(Context context) {
        this.context = context;
        return this;
    }
}
