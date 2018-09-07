package org.aplatanao.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class Context extends HashMap<Class, Object> {

    protected Context parent;

    public Context() {
        super();
    }

    private void setParent(Context parent) {
        this.parent = parent;
    }

    private Context getParent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> c) {
        if (parent != null && parent.containsKey(c)) {
            return (T) parent.get(c);
        }
        if (super.containsKey(c)) {
            return (T) super.get(c);
        }
        return _get(this, new HashSet<>(), c);
    }

    @SuppressWarnings("unchecked")
    private <T> T _get(Class<T> c) {
        return (T) super.get(c);
    }

    @SuppressWarnings("unchecked")
    private static <T> T _get(Context context, Collection<Class> classes, Class<T> c) {
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

            // self reference the factory context
            if (type == Context.class) {
                objects.add((T) context);
                continue;
            }

            // get from parent in factory usage
            if (context.getParent() != null && context.getParent().containsKey(type)) {
                objects.add((T) context.getParent().get(type));
                continue;
            }

            // get instance and use it as object parameter
            Object instance = context._get(type);
            if (instance == null) {
                // store it for recursive check
                classes.add(type);

                instance = _get(context, classes, type);
                context.put(type, instance);

                // remove it from recursive check
                classes.remove(type);
            }
            objects.add(instance);
        }

        try {
            Object instance = constructor.newInstance(objects.toArray());
            context.put(c, instance);
            return (T) instance;
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            String message = "instantiation failure: " + e.getMessage();
            throw new ContextException(message, e);
        }

    }

    public Context createFactory() {
        Context factory = new Context();
        factory.setParent(this);
        return factory;
    }
}
