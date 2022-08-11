package reflection;

import java.lang.reflect.Field;
import static reflection.Utils.*;

public class FieldsReflection {

    public static void set(String fieldName, Object instance, Object value) throws Exception {
        try {

            boolean isStatic = instance instanceof Class<?>;
            Field f = getField(isStatic ? (Class) instance : instance.getClass(), fieldName);

            if(isStatic) {
                setStaticField(f, value);
            } else {
                forceAccessible(f);
                f.set(isStatic ? null : instance, value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object get(String fieldName, Object instance) {
        try {
            Field f = instance instanceof Class<?> ? ((Class<?>) instance).getDeclaredField(fieldName) : instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
