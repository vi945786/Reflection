package reflection;

import sun.misc.Unsafe;

import java.lang.annotation.Native;
import java.lang.reflect.*;
import static reflection.FieldReflection.*;
import static reflection.Vars.*;

public class Utils {

    /**
     * takes an object, makes it accessible and returns it
     * @param o object to be made accessible
     * @return the same object that was passed in but accessible
     */
    public static <T extends AccessibleObject> T forceAccessible(T o, boolean flag) {
        try {
            o.setAccessible(flag);
        } catch (InaccessibleObjectException e) {
            unsafe.putBoolean(o, 12, flag);
        }
        return o;
    }

    /**
     * when you get an IllegalAccessException that says "Can not set [Field] to [type]" use this or add a fieldAccessor to make the field use it
     * @param f field to make writable
     * @param fieldAccessor the fieldAccessor to make the field use
     * @return the field but writable or with the fieldAccessor
     */
    public static Field forceWritable(Field f, Object fieldAccessor) {
        try {
            if(fieldAccessor == null) {
                Field root = (Field) rootField.get(f);
                Object currentOverrideFieldAccessor = overrideFieldAccessorField.get(f);
                if (currentOverrideFieldAccessor == null || setterField.get(currentOverrideFieldAccessor) == null) {
                    Object newOverrideFieldAccessor = newFieldAccessor.invoke(reflectionFactory, root, true);

                    if (setterField.get(newOverrideFieldAccessor) == null) {
                        int fieldFlags = fieldFlagsField.getInt(newOverrideFieldAccessor);
                        if (fieldFlags % 2 == 1) {
                            fieldFlagsField.set(newOverrideFieldAccessor, fieldFlags - 1);
                        }

                        setterField.set(newOverrideFieldAccessor, make.invoke(null, f.getDeclaringClass(), memberNameConstructor.newInstance(f, true)));
                        overrideFieldAccessorField.set(f, newOverrideFieldAccessor);
                    }
                }
            } else {
                overrideFieldAccessorField.set(f, fieldAccessor);
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return f;
    }

    /**
     * makes a new object of type FieldAccessor
     * @param f field to make a FieldAccessor of
     * @param override if it should use root
     * @return a FieldAccessor of the field passed in
     */
    public static Object makeFieldAccessor(Field f, boolean override) {
        try {
            return newFieldAccessor.invoke(reflectionFactory, f, override);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
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
                Field f = getField(o.getClass(), "modifiers", false);

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

    public static void crashJVM() {
        unsafe.getByte(0);
    }
}