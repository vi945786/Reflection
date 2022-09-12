package reflection;

import java.lang.reflect.*;
import static reflection.Utils.*;

public class FieldReflection {

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
        forceAccessible(f);
        forceSet(f, value, instance instanceof Class<?> ? null : instance);
    }

    /**
     * gets field's value
     * @param f field to get value of
     * @param instance instance to get field value of
     * @return value of the field in the instance (null if field is static)
     */
    public static Object getFieldValue(Field f, Object instance) {
        try {
            forceAccessible(f);
            return f.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets field from class and all superclasses
     * @param clazz the class the field is in
     * @param name the name of the field
     * @return the field
     */
    public static Field getField(String name, Class<?> clazz) {
        try {
            Field f = null;
            try {
                f = clazz.getField(name);
                if(f != null) {
                    return f;
                }
            } catch (NoSuchFieldException e) {

            }

            if(f == null) {
                Class superClass = clazz;
                while (f == null && superClass.getSuperclass() != null) {
                    try {
                        f = superClass.getDeclaredField(name);
                        return f;
                    } catch (NoSuchFieldException e) {
                        superClass = superClass.getSuperclass();
                    }
                }
            }

            if (f == null) {
                Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                m.setAccessible(true);
                for (Field field : (Field[]) m.invoke(clazz, false)) {
                    if (field.getName().equals(name)) {

                        Method copy = Field.class.getDeclaredMethod("copy");
                        forceAccessible(copy);

                        return (Field) copy.invoke(field);
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
