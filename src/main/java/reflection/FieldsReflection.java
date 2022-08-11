package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static reflection.Utils.*;

public class FieldsReflection {

    public static void setFieldValue(String fieldName, Object instance, Object value) {
        try {

            boolean isStatic = instance instanceof Class<?>;
            Field f = getField(isStatic ? (Class) instance : instance.getClass(), fieldName);

            if(isStatic) {
                setWithUnsafe(f, value);
            } else {
                forceAccessible(f);
                f.set(isStatic ? null : instance, value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(String fieldName, Object instance) {
        try {
            Field f = instance instanceof Class<?> ? ((Class<?>) instance).getDeclaredField(fieldName) : instance.getClass().getDeclaredField(fieldName);
            forceAccessible(f);
            return f.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
