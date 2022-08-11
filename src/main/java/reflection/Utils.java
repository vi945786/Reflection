package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import static java.util.Map.entry;
import static reflection.Boxing.*;
import static reflection.FieldsReflection.getField;
import static reflection.MethodReflection.getMethod;
import static reflection.MethodReflection.useMethod;

public class Utils {


    /**
     * takes an object, makes it accessible and returns it
     * @param o object to be made accessible
     * @return the same object that was passed in but accessible
     */
    public static Object forceAccessible(Object o) {
        try {
            if(!(o instanceof AccessibleObject)) {
                return o;
            }

            Unsafe unsafe = getUnsafe();

            Field f = getField(AccessibleObject.class, "override");
            unsafe.putBoolean(o, unsafe.objectFieldOffset(f), true);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the variable used for unsafe reflection
     */
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

    /**
     * forcefully change a field's value
     * @param f the field to change
     * @param value the value the field is changing to
     * @param isStatic is the instance static
     * @param instance the instance of the object the field is in
     */
    public static void forceSet(Field f, Object value, boolean isStatic, Object instance) {
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

            Object FieldBase = isStatic ? unsafe.staticFieldBase(f) : instance;
            long FieldOffset = isStatic ? unsafe.staticFieldOffset(f) : unsafe.objectFieldOffset(f);

            Method m = unsafeSet.get(wrapperToPrimitive(value.getClass()));
            if (m != null) {
                m.invoke(unsafe, FieldBase, FieldOffset, value);
            } else {
                unsafe.putObject(FieldBase, FieldOffset, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * change modifiers of object
     * @param o object to change modifiers of
     * @param modifierList modifiers to change to
     * @return object with changed modifiers
     */
    public static Object changeModifiers(Object o, int ... modifierList) {
        try {
            if(modifierList.length == 0) {
                return null;
            }
            Field f = getField(o.getClass(), "modifiers");
            forceAccessible(f);
            Method m = getMethod("getModifiers", o.getClass());
            forceAccessible(m);
            int modifiers = unbox((Integer) useMethod(m, o));

            for(int modifier : modifierList) {
                f.setInt(o, modifiers + modifier);
                m = getMethod("getModifiers", o.getClass());
                modifiers = unbox((Integer) useMethod(m, o));
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
