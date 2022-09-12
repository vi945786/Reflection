import org.junit.jupiter.api.Test;
import vars.FakeClass;
import vars.FakeClass2;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.MethodReflection.*;

public class MethodTest {

    @Test
    public void getPrivateMethodWithout() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod(FakeClass.class, "privateMethod"), fakeClass);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWith() {
        FakeClass fakeClass = new FakeClass("");
        useMethod(getMethod(FakeClass.class, "privateMethod", int.class), fakeClass, 1);
        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithout() {
        useMethod(getMethod(FakeClass.class, "privateStaticMethod"), null);
        assertTrue(FakeClass.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWith() {
        useMethod(getMethod(FakeClass.class, "privateStaticMethod", int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWith);
    }

    @Test
    public void getPrivateMethodWithoutSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod(FakeClass.class, "privateMethod"), fakeClass2);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();
        useMethod(getMethod(FakeClass.class, "privateMethod", int.class), fakeClass2, 1);
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithoutSuperclass() {
        useMethod(getMethod(FakeClass2.class, "privateStaticMethodSuperclass"), null);
        assertTrue(FakeClass.wasStaticMethodUsedSuperclass);
    }

    @Test
    public void getPrivateStaticMethodWithSuperclass() {
        useMethod(getMethod(FakeClass2.class, "privateStaticMethodSuperclass", int.class), null, 1);
        assertTrue(FakeClass.wasStaticMethodUsedWithSuperclass);
    }
}
