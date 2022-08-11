package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import static java.util.Map.entry;
import static reflection.Boxing.*;
import static reflection.FieldsReflection.getField;
import static reflection.MethodReflection.getMethod;

public class Utils {

    public static Object forceAccessible(Object o) {
        try {
            Unsafe unsafe = getUnsafe();
            Field f = getField(AccessibleObject.class, "override");
            Method m = AccessibleObject.class.getDeclaredMethod("setAccessible0", boolean.class);

            unsafe.putBoolean(m, unsafe.objectFieldOffset(f), true);
            m.invoke(o, true);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Unsafe getUnsafe() {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setWithUnsafe(Field f, Object value) {
        try {
            Unsafe unsafe = getUnsafe();
            Map<Class, Method> unsafeSet = Map.ofEntries(
                    entry(byte.class, (Method) forceAccessible(getMethod("putByte", Unsafe.class, Object.class, long.class, byte.class))),
                    entry(short.class, (Method) forceAccessible(getMethod("putShort", Unsafe.class, Object.class, long.class, short.class))),
                    entry(int.class, (Method) forceAccessible(getMethod("putInt", Unsafe.class, Object.class, long.class, int.class))),
                    entry(long.class, (Method) forceAccessible(getMethod("putLong", Unsafe.class, Object.class, long.class, long.class))),
                    entry(float.class, (Method) forceAccessible(getMethod("putFloat", Unsafe.class, Object.class, long.class, float.class))),
                    entry(double.class, (Method) forceAccessible(getMethod("putDouble", Unsafe.class, Object.class, long.class, double.class))),
                    entry(char.class, (Method) forceAccessible(getMethod("putChar", Unsafe.class, Object.class, long.class, char.class))),
                    entry(boolean.class, (Method) forceAccessible(getMethod("putBoolean", Unsafe.class, Object.class, long.class, boolean.class)))
            );

            Object FieldBase = unsafe.staticFieldBase(f);
            long FieldOffset = unsafe.staticFieldOffset(f);

            Method m = unsafeSet.get(wrapperToPrimitive(value.getClass()));
            if (m != null) {
                m.invoke(unsafe, FieldBase, FieldOffset, getWrappersConstructors(value).newInstance(value));
            } else {
                unsafe.putObject(FieldBase, FieldOffset, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
