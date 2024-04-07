package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static reflection.ReflectionUtils.*;

public class FieldReflection {

    private static final Method getDeclaredFields0;
    private static final Method copyField;

    static {
        try {
            getDeclaredFields0 = forceAccessible(Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class), true);
            copyField = forceAccessible(Field.class.getDeclaredMethod("copy"), true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Initialization of FieldReflection failed", e);
        }
    }

    public static void setStaticFieldValue(Field f, Object value) {
        setFieldValue(f, null, value);
    }

    /**
     * sets field's value (doesn't work with trusted final fields)
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
        long offset;
        Object obj;

        if(instance == null) {
            offset = getStaticFieldOffset(f);
            obj = f.getDeclaringClass();
        } else {
            offset = getObjectFieldOffset(f);
            obj = instance;
        }

        switch (f.getType().getName()) {
            case "int" -> unsafe.putInt(obj, offset, (Integer) value);
            case "boolean" -> unsafe.putBoolean(obj, offset, (Boolean) value);
            case "byte" -> unsafe.putByte(obj, offset, (Byte) value);
            case "long" -> unsafe.putLong(obj, offset, (Long) value);
            case "short" -> unsafe.putShort(obj, offset, (Short) value);
            case "float" -> unsafe.putFloat(obj, offset, (Float) value);
            case "char" -> unsafe.putChar(obj, offset, (Character) value);
            case "double" -> unsafe.putDouble(obj, offset, (Double) value);
            default -> unsafe.putObject(obj, offset, value);
        }
    }

    public static Object getStaticFieldValue(Field f) {
        return getFieldValue(f, null);
    }

    /**
     * gets field's value
     * @param f field to get value of
     * @param instance instance to get field value of (null if field is static)
     * @return value of the field in the instance
     */
    public static Object getFieldValue(Field f, Object instance) {
        long offset;
        Object obj;

        if(instance == null) {
            offset = getStaticFieldOffset(f);
            obj = f.getDeclaringClass();
        } else {
            offset = getObjectFieldOffset(f);
            obj = instance;
        }

        return switch (f.getType().getName()) {
            case "int" -> unsafe.getInt(obj, offset);
            case "boolean" -> unsafe.getBoolean(obj, offset);
            case "byte" -> unsafe.getByte(obj, offset);
            case "long" -> unsafe.getLong(obj, offset);
            case "short" -> unsafe.getShort(obj, offset);
            case "float" -> unsafe.getFloat(obj, offset);
            case "char" -> unsafe.getChar(obj, offset);
            case "double" -> unsafe.getDouble(obj, offset);
            default -> unsafe.getObject(obj, offset);
        };
    }

    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
        return getField(clazz, name, false);
    }

    /**
     * gets field from class and all superclasses
     * @param clazz the class the field is in
     * @param name the name of the field
     * @param includeInheritedFields if to search in the superclasses
     * @return the field
     */
    public static Field getField(Class<?> clazz, String name, boolean includeInheritedFields) throws NoSuchFieldException {
        for(Field field : getFields(clazz, includeInheritedFields)) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        throw new NoSuchFieldException(name);
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
                for (Field field : (Field[]) getDeclaredFields0.invoke(clazz, false)) {
                    fields.add((Field) copyField.invoke(field));
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
