package org.aplatanao.context;

import org.aplatanao.context.example.Application;
import org.aplatanao.context.example.ServiceA;
import org.aplatanao.context.example.ServiceB;
import org.aplatanao.context.example.Storage;
import org.aplatanao.context.invalid.MultipleConstructor;
import org.aplatanao.context.invalid.RecursiveRoot;
import org.aplatanao.context.invalid.SelfReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {
    private Context context = new Context();

    @Test
    public void invalid() {
        assertThrows(ContextException.class, () -> context.get(MultipleConstructor.class));
        assertThrows(ContextException.class, () -> context.get(SelfReference.class));
        assertThrows(ContextException.class, () -> context.get(RecursiveRoot.class));
    }

    @Test
    public void example() {
        Application application = context.get(Application.class);
        assertNotNull(application);

        // set and get a value other the application layer
        assertThrows(NullPointerException.class, () -> application.get("foo"));
        application.set("foo", "bar");
        assertEquals("bar", application.get("foo"));

        // test service layer
        ServiceA serviceA = context.get(ServiceA.class);
        assertSame(application.getServiceA(), serviceA);
        assertEquals(application.get("foo"), serviceA.get("foo").getValue());

        // test dependency on service level
        ServiceB serviceB = context.get(ServiceB.class);
        assertSame(application.getServiceB(), serviceB);
        assertSame(serviceB.getServiceA(), serviceA);

        // test storage layer access
        Storage storage = context.get(Storage.class);
        assertSame(context, storage.getContext());
        assertSame(serviceA.getStorage(), storage);
        assertEquals(serviceA.get("foo"), storage.get("foo"));

        // and reverse it
        storage.get("foo").setValue("update");
        assertEquals("update", application.get("foo"));
    }

}
