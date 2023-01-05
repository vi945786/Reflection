package reflection;

import java.lang.reflect.*;
import static reflection.FieldReflection.*;
import static reflection.Vars.*;

public class Utils {

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
        try {
            return overrideField.getBoolean(o);
        } catch (IllegalAccessException e) {
           e.printStackTrace();
        }
        throw new NullPointerException();
    }

    /**
     * change modifiers of object
     * @param o object to change modifiers of
     * @param modifierList modifiers to change to
     * @return object with changed modifier
     */
    public static <T extends AccessibleObject> T changeModifiers(T o, int ... modifierList) {
        try {
            if(modifierList.length != 0) {
                Field f = getField(o.getClass(), "modifiers");

                boolean isOverride = isAccessible(f);

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

    /**
     * casts value to type
     * @param clazz type to cast to
     * @param value value to cast
     * @return value as type
     */
    public static <T> T cast(Class<T> clazz, Object value) {
        return clazz.cast(value);
    }

    /**
     * don't use this it actually works
     */
    public static void crashJVM() {
        unsafe.getByte(0);
    }
}