import org.junit.jupiter.api.Test;
import vars.FakeClass;
import vars.FakeClass2;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.MethodReflection.*;

public class MethodTest {

    @Test
    public void getPrivateMethodWithout() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod("privateMethod", FakeClass.class), fakeClass);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWith() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod("privateMethod", FakeClass.class, int.class), fakeClass, 1);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithout() {
        useMethod(getMethod("privateStaticMethod", FakeClass.class), null);
        assertTrue(FakeClass.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWith() {
        useMethod(getMethod("privateStaticMethod", FakeClass.class, int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWith);
    }

    @Test
    public void getPrivateMethodWithoutSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod("privateMethod", FakeClass.class), fakeClass2);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod("privateMethod", FakeClass.class, int.class), fakeClass2, 1);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithoutSuperclass() {
        useMethod(getMethod("privateStaticMethodSuperclass", FakeClass2.class), null);
        assertTrue(FakeClass.wasStaticMethodUsedSuperclass);
    }

    @Test
    public void getPrivateStaticMethodWithSuperclass() {
        useMethod(getMethod("privateStaticMethodSuperclass", FakeClass2.class, int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWithSuperclass);
    }
}
