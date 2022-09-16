package reflection;

import sun.misc.Unsafe;

import java.lang.reflect.*;

import static reflection.Utils.forceAccessible;

public class Vars {
    //Unsafe.class
    public static Unsafe unsafe;

    //Class.class
    public static Method getDeclaredConstructors0; //gets all constructors
    public static Method getDeclaredFields0; //gets all fields
    public static Method getDeclaredMethods0; //gets all constructors

    //Constructor.class
    public static Method copyConstructor; //adds root

    //Field.class
    public static Method copyField; //adds root
    public static Field overrideFieldAccessorField;
    public static Field rootField;
    public static Field override;

    //Method.class
    public static Method copyMethod; //adds root

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
                getDeclaredConstructors0 = forceAccessible(Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class), true);
                getDeclaredFields0 = forceAccessible(Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class), true);
                getDeclaredMethods0 = forceAccessible(Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class), true);
            }

            {
                copyConstructor = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);
            }

            {
                copyField = forceAccessible(Field.class.getDeclaredMethod("copy"), true);
                overrideFieldAccessorField = forceAccessible((Field) copyField.invoke(((Field[]) getDeclaredFields0.invoke(Field.class, false))[10]), true);
                rootField = forceAccessible((Field) copyField.invoke(((Field[]) getDeclaredFields0.invoke(Field.class, false))[11]), true);
                {
                    Method m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                    m.setAccessible(true);
                    override = forceAccessible(((Field[]) m.invoke(AccessibleObject.class, false))[0], true);
                }
            }

            {
                copyMethod = forceAccessible(Method.class.getDeclaredMethod("copy"), true);
            }

            {
                fieldAccessorClass = Class.forName("jdk.internal.reflect.MethodHandleFieldAccessorImpl");
                fieldFlagsField = forceAccessible(fieldAccessorClass.getDeclaredField("fieldFlags"), true);
                setterField = forceAccessible(fieldAccessorClass.getDeclaredField("setter"), true);
            }

            {
                reflectionFactoryClass = Class.forName("jdk.internal.reflect.ReflectionFactory");
                getReflectionFactory = forceAccessible(reflectionFactoryClass.getDeclaredMethod("getReflectionFactory"), true);
                reflectionFactory = getReflectionFactory.invoke(null);
                newFieldAccessor = forceAccessible(reflectionFactoryClass.getDeclaredMethod("newFieldAccessor", Field.class, boolean.class), true);
            }

            {
                memberNameClass = Class.forName("java.lang.invoke.MemberName");
                memberNameConstructor = forceAccessible(memberNameClass.getDeclaredConstructor(Field.class, boolean.class), true);
            }

            {
                directMethodHandleClass = Class.forName("java.lang.invoke.DirectMethodHandle");
                make = forceAccessible(directMethodHandleClass.getDeclaredMethod("make", Class.class, memberNameClass), true);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
