package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import static reflection.Utils.*;

public class FieldReflection {

    //---getFields---
    //Class.class
    public static Method getDeclaredFields0; //gets all fields

    //Field.class
    public static Method copy; //adds root


    //---setFieldValue---
    //FieldAccessor.class
    public static Class<?> fieldAccessorClass;
    public static Field fieldFlagsField;
    public static Field setterField;

    //ReflectionFactory.class
    public static Class<?> reflectionFactoryClass;
    public static Method getReflectionFactory; //gets the instance
    public static Object reflectionFactory; //the instance
    public static Method newFieldAccessor; //new FieldAccessor

    //MemberName.class
    public static Class<?> memberNameClass;
    public static Constructor<?> memberNameConstructor;

    //DirectMethodHandle.class
    public static Class<?> directMethodHandleClass;
    public static Method make; //makes new instance

    //Field.class
    public static Field overrideFieldAccessorField;
    public static Field rootField;

    static {
        try {
            getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            getDeclaredFields0.setAccessible(true);

            copy = forceAccessible(Field.class.getDeclaredMethod("copy"), true);


            fieldAccessorClass = Class.forName("jdk.internal.reflect.MethodHandleFieldAccessorImpl");
            fieldFlagsField = forceAccessible(fieldAccessorClass.getDeclaredField("fieldFlags"), true);
            setterField = forceAccessible(fieldAccessorClass.getDeclaredField("setter"), true);

            reflectionFactoryClass = Class.forName("jdk.internal.reflect.ReflectionFactory");
            getReflectionFactory = forceAccessible(reflectionFactoryClass.getDeclaredMethod("getReflectionFactory"), true);
            reflectionFactory = getReflectionFactory.invoke(null);
            newFieldAccessor = forceAccessible(reflectionFactoryClass.getDeclaredMethod("newFieldAccessor", Field.class, boolean.class), true);

            memberNameClass = Class.forName("java.lang.invoke.MemberName");
            memberNameConstructor = forceAccessible(memberNameClass.getDeclaredConstructor(Field.class, boolean.class), true);

            directMethodHandleClass = Class.forName("java.lang.invoke.DirectMethodHandle");
            make = forceAccessible(directMethodHandleClass.getDeclaredMethod("make", Class.class, memberNameClass), true);

            overrideFieldAccessorField = forceAccessible((Field) copy.invoke(((Field[]) getDeclaredFields0.invoke(Field.class, false))[10]), true);
            rootField = forceAccessible((Field) copy.invoke(((Field[]) getDeclaredFields0.invoke(Field.class, false))[11]), true);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
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
            Field root = (Field) rootField.get(f);

            Object currentOverrideFieldAccessor = overrideFieldAccessorField.get(f);
            if(currentOverrideFieldAccessor == null || setterField.get(currentOverrideFieldAccessor) == null) {
                Object newOverrideFieldAccessor = newFieldAccessor.invoke(reflectionFactory, root, true);

                if(setterField.get(newOverrideFieldAccessor) == null) {
                    int fieldFlags = fieldFlagsField.getInt(newOverrideFieldAccessor);
                    if(fieldFlags % 2 == 1) {
                        fieldFlagsField.set(newOverrideFieldAccessor, fieldFlags -1);
                    }

                    setterField.set(newOverrideFieldAccessor, make.invoke(null, f.getDeclaringClass(), memberNameConstructor.newInstance(f, true)));
                    overrideFieldAccessorField.set(f, newOverrideFieldAccessor);
                }
            }

            boolean isOverride = override.getBoolean(f);

            if(!isOverride) {
                forceAccessible(f, true);
            }

            f.set(instance, value);

            if(!isOverride) {
                forceAccessible(f, false);
            }

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
                for (Field field : (Field[]) getDeclaredFields0.invoke(clazz, false)) {
                    fields.add((Field) copy.invoke(field));
                }
                if(includeInheritedFields) {
                    clazz = clazz.getSuperclass();
                } else {
                    clazz = null;
                }
            }

            if(fields.isEmpty()) {
                throw new NullPointerException("no fields in class");
            }
            return fields.toArray(new Field[]{fields.get(0)});
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("no fields in class");
    }
}
