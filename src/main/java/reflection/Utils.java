package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.*;
import static reflection.Boxing.*;
import static reflection.FieldReflection.getField;
import static reflection.FieldReflection.getFieldValue;

public class Utils {

    public static Unsafe unsafe = getUnsafe();
    public static Field override = getOverride();

    /**
     * takes an object, makes it accessible and returns it
     * @param o object to be made accessible
     * @return the same object that was passed in but accessible
     */
    public static <T extends AccessibleObject> T forceAccessible(T o, boolean flag) {
        try {
            if (override.getBoolean(o) != flag) {
                try {
                    o.setAccessible(flag);
                } catch (InaccessibleObjectException e) {
                    unsafe.putBoolean(o, unsafe.objectFieldOffset(override), flag);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
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
            unsafe.putBoolean(override, unsafe.objectFieldOffset(override), true);
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
                boolean isOverride = override.getBoolean(f);

                if(!isOverride) {
                    forceAccessible(f, true);
                }

                try {
                    f.set(instance, value);
                    if (instance == null || !instance.equals(value)) {
                        return;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                try {
                    Object FieldBase = instance == null ? unsafe.staticFieldBase(f) : instance;
                    long FieldOffset = instance == null ? unsafe.staticFieldOffset(f) : unsafe.objectFieldOffset(f);

                    Class<?> asPrimitive = wrapperToPrimitive(value.getClass());
                    String asPrimitiveSimple = asPrimitive.getSimpleName();
                    String methodName = "put" + asPrimitiveSimple.substring(0, 1).toUpperCase() + asPrimitiveSimple.substring(1);
                    Method m = Unsafe.class.getMethod(methodName, Object.class, long.class, asPrimitive);

                    m.invoke(unsafe, FieldBase, FieldOffset, value);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(!isOverride) {
                    forceAccessible(f, false);
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
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
            if(modifierList.length != 0) {
                Field f = getField(o.getClass(), "modifiers");

                boolean isOverride = override.getBoolean(f);

                if(!isOverride) {
                    forceAccessible(f, true);
                }

                for (int modifier : modifierList) {
                    int modifiers = (int) getFieldValue(f, o);

                    if (modifier < 0) {
                        if ((modifiers & (-modifier - 1)) != 0) {
                            f.setInt(o, modifiers & modifier);
                        }
                    } else {
                        if ((modifiers & modifier) == 0) {
                            f.setInt(o, modifiers + modifier);
                        }
                    }
                }
                if(!isOverride) {
                    forceAccessible(f, false);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
}