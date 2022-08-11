package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
     * @param instance the instance of the object the field is in
     */
    public static void forceSet(Field f, Object value, Object instance) {
        try {
            Unsafe unsafe = getUnsafe();

            Object FieldBase = instance == null ? unsafe.staticFieldBase(f) : instance;
            long FieldOffset = instance == null ? unsafe.staticFieldOffset(f) : unsafe.objectFieldOffset(f);

            Class asPrimitive = wrapperToPrimitive(value.getClass());
            String methodName = "put" + asPrimitive.getSimpleName().substring(0, 1).toUpperCase() + asPrimitive.getSimpleName().substring(1);
            Method m = (Method) forceAccessible(getMethod(methodName, Unsafe.class, Object.class, long.class, asPrimitive));

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
