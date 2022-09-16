import org.junit.jupiter.api.Test;
import vars.FakeClass;
import vars.FakeClass2;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.ConstructorReflection.getConstructor;
import static reflection.ConstructorReflection.useConstructor;
import static reflection.FieldReflection.*;

public class ConstructorTest {
    @Test
    public void getPrivateConstructorInt() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class, int.class), 1);
        assertEquals(1, getFieldValue(getField(FakeClass.class, "privateFinalField", false), fakeClass));
    }

    @Test
    public void getPrivateConstructorInteger() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class, Integer.class), Integer.valueOf(1));
        assertEquals(1, getFieldValue(getField(FakeClass.class, "privateFinalField", false), fakeClass));
    }

    @Test
    public void getPrivateConstructor() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class));
        assertEquals(1, getFieldValue(getField(FakeClass.class, "privateFinalField", false), fakeClass));
    }
}
