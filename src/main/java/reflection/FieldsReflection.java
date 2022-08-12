package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static reflection.Utils.*;

public class FieldsReflection {

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
        try {
            forceAccessible(f);
            forceSet(f, value, instance instanceof Class<?> ? null : instance);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets field
     * @param clazz the class the field is in
     * @param name the name of the field
     * @return the field
     */
    public static Field getField(Class clazz, String name) {
        try {
            Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            m.setAccessible(true);
            for(Field field : (Field[]) m.invoke(clazz, false)) {
                if(field.getName().equals(name)) {
                    return field;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
