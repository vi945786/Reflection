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
        try {
            putBooleanMethod.invoke(unsafe, o, 12, accessible);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * makes a trusted final field writable
     * @param f the field
     * @param fieldAccessor if you want to make the field writable keep this null
     * @param isOverrideFieldAccessor if to set the overrideFieldAccessor or fieldAccessor
     * @return the field with a new field accessor
     */
    public static Field makeFieldWritable(Field f, Object fieldAccessor, boolean isOverrideFieldAccessor) {
        try {
            Field fieldAccessorField = isOverrideFieldAccessor ? overrideFieldAccessorField : Vars.fieldAccessorField;

            if(fieldAccessor == null) {
                if (javaVersion < 18) {
                    Field root = (Field) rootField.get(f);
                    if (root == null) {
                        root = f;
                    }

                    boolean isFinal = Modifier.isFinal(root.getModifiers());

                    if (isFinal) {
                        changeModifiers(root, ~Modifier.FINAL);
                    }

                    fieldAccessorField.set(f, newFieldAccessorMethod.invoke(reflectionFactory, root, true));

                    if (isFinal) {
                        changeModifiers(root, Modifier.FINAL);
                    }

                } else {
                    Object currentOverrideFieldAccessor = fieldAccessorField.get(f);
                    if (currentOverrideFieldAccessor == null || setterField.get(currentOverrideFieldAccessor) == null) {
                        Object newOverrideFieldAccessor = newFieldAccessorMethod.invoke(reflectionFactory, f, true);

                        if (setterField.get(newOverrideFieldAccessor) == null) {
                            int fieldFlags = fieldFlagsField.getInt(newOverrideFieldAccessor);
                            if (fieldFlags % 2 == 1) {
                                fieldFlagsField.set(newOverrideFieldAccessor, fieldFlags - 1);
                            }

                            setterField.set(newOverrideFieldAccessor, makeMethod.invoke(null, f.getDeclaringClass(), memberNameConstructor.newInstance(f, true)));
                            fieldAccessorField.set(f, newOverrideFieldAccessor);
                        }
                    }
                }
            } else {
                if(newFieldAccessorMethod.invoke(reflectionFactory, f, true).getClass().equals(fieldAccessor.getClass())) {
                    fieldAccessorField.set(f, fieldAccessor);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return f;
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
                Field f = getField(o.getClass(), "modifiers", false);

                boolean isOverride = overrideField.getBoolean(f);

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
        try {
            getByteMethod.invoke(unsafe, 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}