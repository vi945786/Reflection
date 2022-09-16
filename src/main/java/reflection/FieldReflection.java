package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static reflection.ConstructorReflection.getConstructor;
import static reflection.MethodReflection.getMethod;
import static reflection.Utils.*;

public class FieldReflection {

    public static Field fieldAccessorField;
    public static Field overrideFieldAccessorField;
    public static Class<?> reflectionFactoryClass;
    public static Method getReflectionFactory;
    public static Object reflectionFactory;
    public static Method newFieldAccessor;
    public static Class<?> fieldAccessorClass;
    public static Field isReadOnly;
    public static Field setterField;
    public static Class<?> memberNameClass;
    public static Constructor<?> newMemberNameClass;
    public static Class<?> directMethodHandle;
    public static Method make;

    static {
        try {
            fieldAccessorField = getField(Field.class, "fieldAccessor");
            forceAccessible(fieldAccessorField, true);

            overrideFieldAccessorField = getField(Field.class, "overrideFieldAccessor");
            forceAccessible(overrideFieldAccessorField, true);

            reflectionFactoryClass = Class.forName("jdk.internal.reflect.ReflectionFactory");

            getReflectionFactory = reflectionFactoryClass.getDeclaredMethod("getReflectionFactory");
            forceAccessible(getReflectionFactory, true);

            reflectionFactory = getReflectionFactory.invoke(null);

            newFieldAccessor = reflectionFactoryClass.getDeclaredMethod("newFieldAccessor", Field.class, boolean.class);
            forceAccessible(newFieldAccessor, true);

            fieldAccessorClass = Class.forName("jdk.internal.reflect.MethodHandleFieldAccessorImpl");

            isReadOnly = getField(fieldAccessorClass, "fieldFlags");
            forceAccessible(isReadOnly, true);

            setterField = getField(fieldAccessorClass, "setter");
            forceAccessible(setterField, true);

            memberNameClass = Class.forName("java.lang.invoke.MemberName");

            newMemberNameClass = getConstructor(memberNameClass, Field.class, boolean.class);
            forceAccessible(newMemberNameClass, true);

            directMethodHandle = Class.forName("java.lang.invoke.DirectMethodHandle");

            make = getMethod(directMethodHandle, "make", Class.class, memberNameClass);
            forceAccessible(make, true);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets field's value
     * @param f field to change
     * @param instance instance to change the field in (null if field is static)
     * @param value the value to change the field to
     */
    public static void setFieldValue(Field f, Object instance, Object value) {
        try {
            Field root = (Field) forceAccessible(getField(Field.class, "root"), true).get(f);

            Object currentOverrideFieldAccessor = overrideFieldAccessorField.get(f);
            if(currentOverrideFieldAccessor == null || setterField.get(currentOverrideFieldAccessor) == null) {
                Object newOverrideFieldAccessor = newFieldAccessor.invoke(reflectionFactory, root, true);

                if(setterField.get(newOverrideFieldAccessor) == null) {
                    int isReadOnlyValue = isReadOnly.getInt(newOverrideFieldAccessor);
                    if(isReadOnlyValue % 2 == 1) {
                        isReadOnly.set(newOverrideFieldAccessor, isReadOnlyValue -1);
                    }

                    setterField.set(newOverrideFieldAccessor, make.invoke(null, f.getDeclaringClass(), newMemberNameClass.newInstance(f, true)));
                    overrideFieldAccessorField.set(f, newOverrideFieldAccessor);
                }
            }

            forceAccessible(f, true);
            f.set(instance, value);

            overrideFieldAccessorField.set(f, currentOverrideFieldAccessor);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
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
        throw new NullPointerException("value not found");
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
