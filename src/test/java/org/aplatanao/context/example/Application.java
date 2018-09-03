package org.aplatanao.context.example;

public class Application {

    private ServiceA serviceA;
    private ServiceB serviceB;

    public Application(ServiceA serviceA, ServiceB serviceB) {
        this.serviceA = serviceA;
        this.serviceB = serviceB;
    }

    public String get(String key) {
        return serviceA.get(key).getValue();
    }

    public void set(String key, String value) {
        serviceA.add(new Entry(key, value));
    }

    public ServiceA getServiceA() {
        return serviceA;
    }

    public ServiceB getServiceB() {
        return serviceB;
    }
}