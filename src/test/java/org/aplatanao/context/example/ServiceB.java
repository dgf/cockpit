package org.aplatanao.context.example;

public class ServiceB {

    private ServiceA serviceA;
    private Storage storage;

    public ServiceB(ServiceA serviceA, Storage storage) {
        this.serviceA = serviceA;
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

    public ServiceA getServiceA() {
        return serviceA;
    }
}
