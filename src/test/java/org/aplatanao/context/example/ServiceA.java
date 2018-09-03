package org.aplatanao.context.example;

public class ServiceA {

    private Storage storage;

    public ServiceA(Storage storage) {
        this.storage = storage;
    }

    public void add(Entry entry) {
        storage.put(entry.getKey(), entry);
    }

    public Entry get(String key) {
        return storage.get(key);
    }

    public Storage getStorage() {
        return storage;
    }
}
