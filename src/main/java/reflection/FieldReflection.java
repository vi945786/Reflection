package reflection;

import sun.misc.Unsafe;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static reflection.Boxing.wrapperToPrimitive;
import static reflection.Utils.*;

public class FieldReflection {

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     * @param isStatic is the field static
     */
    public static void setFieldValue(Field f, Object instance, Object value, boolean isStatic) {
        instance = isStatic ? null : instance;
        if(instance == null || !instance.equals(value)) {
            try {
                boolean isOverride = override.getBoolean(f);

                if(!isOverride) {
                    forceAccessible(f, true);
                }

                try {
                    if(instance == null) {
                        throw new IllegalArgumentException();
                    }
                    f.set(instance, value);
                    if (instance == null || !instance.equals(value)) {
                        return;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    try {
                        Object FieldBase = instance == null ? unsafe.staticFieldBase(f) : instance;
                        long FieldOffset = instance == null ? unsafe.staticFieldOffset(f) : unsafe.objectFieldOffset(f);

                        Class<?> asPrimitive = wrapperToPrimitive(value.getClass());
                        String asPrimitiveSimple = asPrimitive.getSimpleName();
                        String methodName = "put" + asPrimitiveSimple.substring(0, 1).toUpperCase() + asPrimitiveSimple.substring(1);
                        Method m = Unsafe.class.getMethod(methodName, Object.class, long.class, asPrimitive);

                        m.invoke(unsafe, FieldBase, FieldOffset, value);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e2) {
                        e2.printStackTrace();
                    }
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
     * gets field's value
     * @param f field to get value of
     * @param instance instance to get field value of (null if field is static)
     * @return value of the field in the instance
     */
    public static Object getFieldValue(Field f, Object instance) {
        try {
            if(Modifier.isStatic(f.getModifiers()) && instance != null) {
                instance = null;
            } else if(!Modifier.isStatic(f.getModifiers()) && instance == null) {
                throw new NullPointerException("instance can't be null if field isn't static");
            }

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
        throw new NullPointerException("no fields in class");
    }
}
