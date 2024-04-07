package reflection;

import sun.misc.Unsafe;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static reflection.FieldReflection.*;

public class ReflectionUtils {

    static final Unsafe unsafe;
    private final static Object theInternalUnsafe;
    private final static Method objectFieldOffset0;
    private final static Method staticFieldOffset0;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);

            Field theInternalUnsafeField = forceAccessible(Unsafe.class.getDeclaredField("theInternalUnsafe"), true);
            theInternalUnsafe = theInternalUnsafeField.get(null);

            Class<?> theInternalUnsafeClass = theInternalUnsafe.getClass();
            objectFieldOffset0 = forceAccessible(theInternalUnsafeClass.getDeclaredMethod("objectFieldOffset0", Field.class), true);
            staticFieldOffset0 = forceAccessible(theInternalUnsafeClass.getDeclaredMethod("staticFieldOffset0", Field.class), true);
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException e) {
            throw new RuntimeException("Initialization of ReflectionUtils failed", e);
        }
    }

    static long getObjectFieldOffset(Field f) {
        try {
            return (long) objectFieldOffset0.invoke(theInternalUnsafe, f);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static long getStaticFieldOffset(Field f) {
        try {
            return (long) staticFieldOffset0.invoke(theInternalUnsafe, f);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * takes an object, makes it accessible and returns it
     * @param o object to be made accessible
     * @param accessible should be accessible
     * @return the same object that was passed in but accessible
     */
    public static <T extends AccessibleObject> T forceAccessible(T o, boolean accessible) {
        unsafe.putBoolean(o, 12, accessible);
        return o;
    }

    public static Boolean isAccessible(AccessibleObject o) {
        return unsafe.getBoolean(o, 12);
    }

    static String methodToString(Class<?> clazz, String name, Class<?>[] argTypes) {
        return clazz.getName() + '.' + name +
                ((argTypes == null || argTypes.length == 0) ?
                        "()" :
                        Arrays.stream(argTypes)
                                .map(c -> c == null ? "null" : c.getName())
                                .collect(Collectors.joining(",", "(", ")")));
    }
}