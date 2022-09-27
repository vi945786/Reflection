package reflection;

import java.lang.reflect.*;
import static reflection.FieldReflection.getField;
import static reflection.Utils.*;

public class Vars {

    //other
    public static int javaVersion;

    //Unsafe.class
    public static Class<?> unsafeClass;
    public static Object unsafe;
    public static Method putBooleanMethod;
    public static Method getByteMethod;

    //Class.class
    public static Method getDeclaredMethods0Method; //gets all constructors
    public static Method getDeclaredConstructors0Method; //gets all constructors
    public static Method getDeclaredFields0Method; //gets all fields
    public static Method getDeclaredClasses0Method; //gets all inner Classes

    //Constructor.class
    public static Method copyConstructorMethod; //adds root

    //Field.class
    public static Method copyFieldMethod; //adds root

    //AccessibleObject.class
    public static Field overrideField;

    //Method.class
    public static Method copyMethodMethod; //adds root

    static {
        try {

            {
                javaVersion = Integer.parseInt(System.getProperties().getProperty("java.specification.version").replace("1.", ""));
            }

            {
                {
                    unsafeClass = Class.forName("sun.misc.Unsafe");
                    {
                        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
                        unsafeField.setAccessible(true);
                        unsafe = unsafeField.get(null);
                    }
                    {
                        putBooleanMethod = unsafeClass.getDeclaredMethod("putBoolean", Object.class, long.class, boolean.class);
                        putBooleanMethod.setAccessible(true);
                    }
                    getByteMethod = forceAccessible(unsafeClass.getDeclaredMethod("getByte", long.class), true);
                }
            }

            {
                getDeclaredMethods0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class), true);
                getDeclaredConstructors0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class), true);
                getDeclaredFields0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class), true);
                getDeclaredClasses0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredClasses0"), true);
            }

            {
                copyConstructorMethod = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);
            }

            {
                copyFieldMethod = forceAccessible(Field.class.getDeclaredMethod("copy"), true);
            }

            {
                overrideField = forceAccessible(getField(AccessibleObject.class, "override", false), true);
            }

            {
                copyMethodMethod = forceAccessible(Method.class.getDeclaredMethod("copy"), true);
            }
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
