import org.junit.jupiter.api.Test;
import reflection.MethodReflection;
import vars.FakeClass;

import static org.junit.jupiter.api.Assertions.*;

public class MethodTest {
    @Test
    public void getPrivateMethodWithout() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("privateMethod", fakeClass);

        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWith() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("privateMethod", fakeClass, 1);

        assertTrue(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithout() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("privateStaticMethod", fakeClass);

        assertTrue(fakeClass.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWith() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("privateStaticMethod", fakeClass, 1);

        assertTrue(fakeClass.wasStaticMethodUsedWith);
    }


    @Test
    public void getPrivateMethodWithoutWrong() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("wrong", fakeClass);

        assertFalse(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithWrong() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("wrong", fakeClass, 1);

        assertFalse(fakeClass.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithoutWrong() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("Wrong", fakeClass);

        assertFalse(fakeClass.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithWrong() {
        FakeClass fakeClass = new FakeClass("");

        MethodReflection.get("Wrong", fakeClass, 1);

        assertFalse(fakeClass.wasStaticMethodUsedWith);
    }
}