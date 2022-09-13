package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static reflection.Utils.*;

public class FieldReflection {

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
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
            boolean isOverride = override.getBoolean(f);

            if(!isOverride) {
                forceAccessible(f, true);
            }

            Object value = f.get(instance);

            if(!isOverride) {
                forceAccessible(f, false);
            }

            return value;
        } catch (IllegalAccessException e) {
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
    public static Field getField(Class<?> clazz, String name) {
            for(Field field : getFields(clazz, true)) {
                if (field.getName().equals(name)) {
                    return field;
                }
            }
        return null;
    }

    /**
     * gets all fields from class and all superclasses if specified
     * @param clazz the class the field is in
     * @param includeInheritedFields if to get fields from superclasses
     * @return the fields specified
     */
    public static Field[] getFields(Class<?> clazz, boolean includeInheritedFields) {
        try {
            List<Field> fields = new ArrayList<>();

            Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            m.setAccessible(true);

            while (clazz != null) {
                for (Field field : (Field[]) m.invoke(clazz, false)) {

                    Method copy = Field.class.getDeclaredMethod("copy");
                    forceAccessible(copy, true);

                    fields.add((Field) copy.invoke(field));
                }
                if(includeInheritedFields) {
                    clazz = clazz.getSuperclass();
                }
            }

            return fields.toArray(new Field[]{fields.get(0)});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
