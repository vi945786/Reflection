package vars;

public class TestVar {
    private final int privateFinalField;
    private volatile int privateVolatileField;
    private static final int privateStaticFinalField = 0;
    private static int privateStaticField;
    private static final int privateStaticFinalFieldSuperclass = 0;
    public boolean wasMethodUsed = false;
    public static boolean wasStaticMethodUsedWith = false;
    public static boolean wasStaticMethodUsed = false;
    public static boolean wasStaticMethodUsedWithSuperclass = false;
    public static boolean wasStaticMethodUsedSuperclass = false;

    private TestVar() {
        privateFinalField = 1;
    }

    private TestVar(int i) {
        privateFinalField = 1;
    }

    private TestVar(Integer i) {
        privateFinalField = 1;
    }

    public TestVar(String s) {
        privateFinalField = 0;
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
