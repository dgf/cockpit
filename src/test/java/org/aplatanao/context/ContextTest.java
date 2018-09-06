package org.aplatanao.context;

import org.aplatanao.context.example.*;
import org.aplatanao.context.invalid.MultipleConstructor;
import org.aplatanao.context.invalid.RecursiveRoot;
import org.aplatanao.context.invalid.SelfReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {
    private Context context;

    @BeforeEach
    public void setup() {
        context = new Context();
    }

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

    @Test
    public void emptyFactory() {
        Entry entry = context.create(Entry.class);
        entry.setKey("one").setValue("two").save();

        Storage storage = context.get(Storage.class);
        assertNull(storage.get("one"));
        assertNotSame(storage, entry.getStorage());
    }

    @Test
    public void initializedFactory() {
        Storage storage = context.get(Storage.class);

        Entry entry = context.create(Entry.class);
        entry.setKey("one").setValue("two").save();

        assertEquals("two", storage.get("one").getValue());
        assertSame(storage, entry.getStorage());
    }
}
