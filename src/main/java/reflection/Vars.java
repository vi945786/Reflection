package reflection;

import sun.misc.Unsafe;

import java.lang.reflect.*;

import static reflection.Utils.forceAccessible;

public class Vars {
    //Unsafe.class
    public static Unsafe unsafe;

    //Class.class
    public static Method getDeclaredConstructors0Method; //gets all constructors
    public static Method getDeclaredFields0Method; //gets all fields
    public static Method getDeclaredMethods0Method; //gets all constructors

    //Constructor.class
    public static Method copyConstructorMethod; //adds root

    //Field.class
    public static Method copyFieldMethod; //adds root
    public static Field overrideFieldAccessorField;
    public static Field rootField;
    public static Field overrideField;

    //Method.class
    public static Method copyMethodMethod; //adds root

    //FieldAccessor.class
    public static Class<?> fieldAccessorClass;
    public static Field fieldFlagsField;
    public static Field setterField;

    //ReflectionFactory.class
    public static Class<?> reflectionFactoryClass;
    public static Method getReflectionFactoryMethod; //gets the instance
    public static Object reflectionFactory; //the instance
    public static Method newFieldAccessorMethod; //new FieldAccessor

    //MemberName.class
    public static Class<?> memberNameClass;
    public static Constructor<?> memberNameConstructor;

    //DirectMethodHandle.class
    public static Class<?> directMethodHandleClass;
    public static Method makeMethod; //makes new instance

    static {
        try {
            {
                {
                    Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                    unsafeField.setAccessible(true);
                    unsafe = (Unsafe) unsafeField.get(null);
                }
            }

            {
                getDeclaredConstructors0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class), true);
                getDeclaredFields0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class), true);
                getDeclaredMethods0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class), true);
            }

            {
                copyConstructorMethod = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);
            }

            {
                copyFieldMethod = forceAccessible(Field.class.getDeclaredMethod("copy"), true);
                overrideFieldAccessorField = forceAccessible((Field) copyFieldMethod.invoke(((Field[]) getDeclaredFields0Method.invoke(Field.class, false))[10]), true);
                rootField = forceAccessible((Field) copyFieldMethod.invoke(((Field[]) getDeclaredFields0Method.invoke(Field.class, false))[11]), true);
                overrideField = forceAccessible(((Field[]) getDeclaredFields0Method.invoke(AccessibleObject.class, false))[0], true);
            }

            {
                copyMethodMethod = forceAccessible(Method.class.getDeclaredMethod("copy"), true);
            }

            {
                fieldAccessorClass = Class.forName("jdk.internal.reflect.MethodHandleFieldAccessorImpl");
                fieldFlagsField = forceAccessible(fieldAccessorClass.getDeclaredField("fieldFlags"), true);
                setterField = forceAccessible(fieldAccessorClass.getDeclaredField("setter"), true);
            }

            {
                reflectionFactoryClass = Class.forName("jdk.internal.reflect.ReflectionFactory");
                getReflectionFactoryMethod = forceAccessible(reflectionFactoryClass.getDeclaredMethod("getReflectionFactory"), true);
                reflectionFactory = getReflectionFactoryMethod.invoke(null);
                newFieldAccessorMethod = forceAccessible(reflectionFactoryClass.getDeclaredMethod("newFieldAccessor", Field.class, boolean.class), true);
            }

            {
                memberNameClass = Class.forName("java.lang.invoke.MemberName");
                memberNameConstructor = forceAccessible(memberNameClass.getDeclaredConstructor(Field.class, boolean.class), true);
            }

            {
                directMethodHandleClass = Class.forName("java.lang.invoke.DirectMethodHandle");
                makeMethod = forceAccessible(directMethodHandleClass.getDeclaredMethod("make", Class.class, memberNameClass), true);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
