package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import static reflection.Utils.*;
import static reflection.Vars.*;

public class FieldReflection {

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
        try {
            Object oldOverrideFieldAccessor = overrideFieldAccessorField.get(f);

            makeFieldWritable(f, null, true);

            boolean isOverride = overrideField.getBoolean(f);

            if(!isOverride) {
                forceAccessible(f, true);
            }

            f.set(instance, value);

            if(!isOverride) {
                forceAccessible(f, false);
            }

            makeFieldWritable(f, oldOverrideFieldAccessor, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets field's value
     * @param f field to get value of
     * @param instance instance to get field value of (null if field is static)
     * @return value of the field in the instance
     */
    public static Object getFieldValue(Field f, Object instance) {
        try {
            boolean isOverride = overrideField.getBoolean(f);

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
        throw new NullPointerException("value not found");
    }

    /**
     * gets field from class and all superclasses
     * @param clazz the class the field is in
     * @param name the name of the field
     * @param includeInheritedFields if to search in the superclasses
     * @return the field
     */
    public static Field getField(Class<?> clazz, String name, boolean includeInheritedFields) {
            for(Field field : getFields(clazz, includeInheritedFields)) {
                if (field.getName().equals(name)) {
                    return field;
                }
            }
        throw new NullPointerException("field doesn't exist");
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

            while (clazz != null) {
                for (Field field : (Field[]) getDeclaredFields0Method.invoke(clazz, false)) {
                    fields.add((Field) copyFieldMethod.invoke(field));
                }
                if(includeInheritedFields) {
                    clazz = clazz.getSuperclass();
                } else {
                    clazz = null;
                }
            }

            return fields.toArray(new Field[0]);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Field[0];
    }
}
