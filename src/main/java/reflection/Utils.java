package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.*;
import static reflection.Boxing.*;
import static reflection.FieldReflection.getField;
import static reflection.MethodReflection.getMethod;
import static reflection.MethodReflection.useMethod;

public class Utils {

    public static Unsafe unsafe = getUnsafe();
    public static Field override = getOverride();

    /**
     * takes an object, makes it accessible and returns it
     * @param o object to be made accessible
     * @return the same object that was passed in but accessible
     */
    public static <T extends AccessibleObject> T forceAccessible(T o) {
        if (!o.isAccessible()) {
            try {
                o.setAccessible(true);
            } catch (InaccessibleObjectException e) {
                unsafe.putBoolean(o, unsafe.objectFieldOffset(override), true);
            }
        }
        return o;
    }

    /**
     * @return the variable used for unsafe reflection
     */
    public static Unsafe getUnsafe() {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return (Unsafe) unsafeField.get(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the field "override" from AccessibleObject.class
     */
    public static Field getOverride() {
        try {
            Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            m.setAccessible(true);
            Field override = ((Field[]) m.invoke(AccessibleObject.class, false))[0];
            return override;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * forcefully change a field's value
     * @param f the field to change
     * @param value the value the field is changing to
     * @param instance the instance of the object the field is in (null if field is static)
     */
    public static void forceSet(Field f, Object value, Object instance) {
        if(instance == null || !instance.equals(value)) {

            try {
                f.set(instance, value);
                if(instance == null || !instance.equals(value)) {
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {

            }

            try {
                Object FieldBase = instance == null ? unsafe.staticFieldBase(f) : instance;
                long FieldOffset = instance == null ? unsafe.staticFieldOffset(f) : unsafe.objectFieldOffset(f);

                Class asPrimitive = wrapperToPrimitive(value.getClass());
                String asPrimitiveSimple = asPrimitive.getSimpleName();
                String methodName = "put" + asPrimitiveSimple.substring(0, 1).toUpperCase() + asPrimitiveSimple.substring(1);
                Method m = Unsafe.class.getMethod(methodName, Object.class, long.class, asPrimitive);

                m.invoke(unsafe, FieldBase, FieldOffset, value);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

            }
        }
    }

    /**
     * change modifiers of object
     * @param o object to change modifiers of
     * @param modifierList modifiers to change to
     * @return object with changed modifier
     */
    public static Object changeModifiers(AccessibleObject o, int ... modifierList) {
        try {
            if(modifierList.length == 0) {
                return null;
            }
            Field f = getField("modifiers", o.getClass());
            forceAccessible(f);
            Method m = getMethod("getModifiers", o.getClass());
            forceAccessible(m);
            int modifiers = unbox((Integer) useMethod(m, o));

            for(int modifier : modifierList) {
                if(modifier < 0) {
                    f.setInt(o, modifiers & modifier);
                } else {
                    f.setInt(o, modifiers + modifier);
                }
                m = getMethod("getModifiers", o.getClass());
                modifiers = unbox((Integer) useMethod(m, o));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
}