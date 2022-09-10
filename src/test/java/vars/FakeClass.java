package vars;

public class FakeClass {
    private final int privateFinalField;
    private static final int privateStaticFinalField = 0;
    private static final int privateStaticFinalFieldSuperclass = 0;
    public Boolean wasMethodUsed = false;
    public static Boolean wasStaticMethodUsedWith = false;
    public static Boolean wasStaticMethodUsed = false;
    public static Boolean wasStaticMethodUsedWithSuperclass = false;
    public static Boolean wasStaticMethodUsedSuperclass = false;

    private FakeClass() {
        privateFinalField = 1;
    }

    private FakeClass(int i) {
        privateFinalField = 1;
    }

    private FakeClass(Integer i) {
        privateFinalField = 1;
    }

    public FakeClass(String s) {
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
}
