package vars;

public class TestVar {

    public final int publicFinalInt;
    public final Object publicFinalObject = new Object();
    private static long privateStaticLong = 0;
    public final int usedContructor;
    private volatile float privateVolatileFloat = 0;

    public boolean wasMethodUsed = false;
    public static boolean wasStaticMethodUsed = false;
    public static boolean wasStaticMethodUsedWith = false;
    public static boolean wasStaticMethodUsedSuperclass = false;


    private void privateMethod() {
        wasMethodUsed = true;
    }

    private int privateMethod(int i) {
        wasMethodUsed = true;
        return i;
    }

    private static void privateStaticMethod() {
        wasStaticMethodUsed = true;
    }

    private static int privateStaticMethod(int i) {
        wasStaticMethodUsedWith = true;
        return i;
    }

    private static void privateStaticMethodSuperclass() {
        wasStaticMethodUsedSuperclass = true;
    }

    public static long getPrivateStaticLong() {
        return privateStaticLong;
    }

    public float getPrivateVolatileFloat() {
        return privateVolatileFloat;
    }

    public TestVar() {
        publicFinalInt = 0;
        usedContructor = 1;
    }

    public TestVar(int i) {
        publicFinalInt = 0;
        usedContructor = 1;
    }

    TestVar(Object obj, char c) {
        publicFinalInt = 0;
        usedContructor = 1;
    }
}
