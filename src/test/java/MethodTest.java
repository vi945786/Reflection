import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;
import vars.FakeClass;
import vars.FakeClass2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;
import static reflection.FieldReflection.getFieldValue;
import static reflection.MethodReflection.*;
import static reflection.Utils.forceAccessible;

public class MethodTest {

    @Test
    public void getPrivateMethodWithout() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod(FakeClass.class, "privateMethod", false), fakeClass);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWith() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod(FakeClass.class, "privateMethod", false, int.class), fakeClass, 1);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithout() {
        useMethod(getMethod(FakeClass.class, "privateStaticMethod", false), null);
        assertTrue(FakeClass.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWith() {
        useMethod(getMethod(FakeClass.class, "privateStaticMethod", false, int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWith);
    }

    @Test
    public void getPrivateMethodWithoutSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod(FakeClass.class, "privateMethod", false), fakeClass2);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod(FakeClass.class, "privateMethod", false, int.class), fakeClass2, 1);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithoutSuperclass() {
        useMethod(getMethod(FakeClass2.class, "privateStaticMethodSuperclass", true), null);
        assertTrue(FakeClass.wasStaticMethodUsedSuperclass);
    }

    @Test
    public void getPrivateStaticMethodWithSuperclass() {
        useMethod(getMethod(FakeClass2.class, "privateStaticMethodSuperclass", true, int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWithSuperclass);
    }

    @Test
    public void getUnusable() {
        Object o = getFieldValue(getField(Unsafe.class, "theInternalUnsafe", false), null);
        Field f = getField(FakeClass.class, "privateFinalField", false);
        Method m = getMethod(o.getClass(), "objectFieldOffset0", false, Field.class);
        assertDoesNotThrow(() -> useMethod(m, o, f));
        assertNotNull(useMethod(m, o, f));
    }
}
