package vars;

public class TestVar {
    public final int publicFinalField;
    private volatile int privateVolatileField;
    private static int privateStaticField;
    public boolean wasMethodUsed = false;
    public static boolean wasStaticMethodUsedWith = false;
    public static boolean wasStaticMethodUsed = false;
    public static boolean wasStaticMethodUsedWithSuperclass = false;
    public static boolean wasStaticMethodUsedSuperclass = false;

    private TestVar() {
        publicFinalField = 1;
    }

    private TestVar(int i) {
        publicFinalField = 1;
    }

    private TestVar(Integer i) {
        publicFinalField = 1;
    }

    public TestVar(String s) {
        publicFinalField = 0;
    }

    private void privateMethod() {
        wasMethodUsed = true;
    }

    private void privateMethod(int i) {
        wasMethodUsed = true;
    }

    private static void privateStaticMethod() {
        wasStaticMethodUsed = true;
    }

    private static void privateStaticMethod(int i) {
        wasStaticMethodUsedWith = true;
    }

    private static void privateStaticMethodSuperclass() {
        wasStaticMethodUsedSuperclass = true;
    }

    private static void privateStaticMethodSuperclass(int i) {
        wasStaticMethodUsedWithSuperclass = true;
    }

    class InnerClass {}
    static class StaticInnerClass {}
}
