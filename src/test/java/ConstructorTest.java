import org.junit.jupiter.api.Test;
import vars.FakeClass;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.ConstructorReflection.getConstructor;
import static reflection.ConstructorReflection.useConstructor;
import static reflection.FieldsReflection.*;

public class ConstructorTest {
    @Test
    public void getPrivateConstructorInt() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class, int.class), 1);
        assertEquals(1, getFieldValue("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorInteger() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class, Integer.class), new Integer(1));
        assertEquals(1, getFieldValue("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructor() {
        FakeClass fakeClass = (FakeClass) useConstructor(getConstructor(FakeClass.class));
        assertEquals(1, getFieldValue("privateFinalField", fakeClass));
    }
}
