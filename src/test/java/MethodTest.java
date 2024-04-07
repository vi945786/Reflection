import org.junit.jupiter.api.Test;
import vars.TestVar;
import vars.TestVar2;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getFieldValue;
import static reflection.FieldReflection.getStaticFieldValue;
import static reflection.MethodReflection.*;

public class MethodTest {

    @Test
    public void getPrivateMethodWithout() {
        TestVar testVar = new TestVar();
        try {
            useMethod(getMethod(TestVar.class, "privateMethod"), testVar);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(testVar.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithAndReturnValue() {
        TestVar testVar = new TestVar();
        int i = 1;
        try {
            assertEquals(i, useMethod(getMethod(TestVar.class, "privateMethod", int.class), testVar, i));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(testVar.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithout() {
        try {
            useStaticMethod(getMethod(TestVar.class, "privateStaticMethod"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        assertTrue(TestVar.wasStaticMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWith() {
        int i = 1;

        try {
            assertEquals(i, useStaticMethod(getMethod(TestVar.class, "privateStaticMethod", int.class), i));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(TestVar.wasStaticMethodUsedWith);
    }

    @Test
    public void getPrivateMethodWithoutSuperclass() {
        TestVar2 fakeClass2 = new TestVar2();
        try {
            useMethod(getMethod(TestVar.class, "privateMethod"), fakeClass2);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateMethodWithSuperclass() {
        TestVar2 fakeClass2 = new TestVar2();
        try {
            useMethod(getMethod(TestVar.class, "privateMethod", int.class), fakeClass2, 1);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(fakeClass2.wasMethodUsed);
    }

    @Test
    public void getPrivateStaticMethodWithoutSuperclass() {
        try {
            useStaticMethod(getMethod(TestVar2.class, "privateStaticMethodSuperclass", true));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertTrue(TestVar.wasStaticMethodUsedSuperclass);
    }

    @Test
    public void getUnusable() {
        try {
            assertEquals(getStaticFieldValue(Class.forName("jdk.internal.org.objectweb.asm.Type").getField("BYTE_TYPE")), useMethod(Class.forName("jdk.internal.org.objectweb.asm.Type").getDeclaredMethod("getType", String.class), null, "B"));
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
