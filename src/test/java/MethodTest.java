import org.junit.jupiter.api.Test;
import vars.FakeClass;

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
}
