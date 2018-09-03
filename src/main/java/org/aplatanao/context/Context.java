package org.aplatanao.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class Context extends HashMap<Class, Object> {

    public Context() {
        super();
        // self referencing instance
        put(getClass(), this);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> c) {
        if (super.containsKey(c)) {
            return (T) super.get(c);
        }
        return _get(new HashSet<>(), c);
    }

    @SuppressWarnings("unchecked")
    private <T> T _get(Collection<Class> classes, Class<T> c) {
        Constructor[] constructors = c.getDeclaredConstructors();

        if (constructors.length != 1) {
            String message = "context supports only one constructor, found: " + constructors.length;
            throw new ContextException(message);
        }

        Constructor<?> constructor = constructors[0];
        Parameter[] parameters = constructor.getParameters();
        List<Object> objects = new ArrayList<>();

        for (Parameter p : parameters) {
            Class<?> type = p.getType();

            // self referenced
            if (c.equals(type)) {
                String message = "self referencing constructor: " + c.getName();
                throw new ContextException(message);
            }

            // check reference loop
            if (classes.contains(type)) {
                String message = "dependency loop: " + type.getName();
                throw new ContextException(message);
            }

            // get instance and use it as object parameter
            Object instance = super.get(type);
            if (instance == null) {
                // store it for recursive check
                classes.add(type);

                instance = _get(classes, type);
                super.put(type, instance);

                classes.remove(type);
            }
            objects.add(instance);
        }

        try {
            Object instance = constructor.newInstance(objects.toArray());
            put(c, instance);
            return (T) instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String message = "instantiation failure: " + e.getMessage();
            throw new ContextException(message, e);
        }
    }
}
