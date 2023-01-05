package reflection;

import sun.misc.Unsafe;

import java.lang.management.ManagementFactory;
import java.lang.reflect.*;
import static reflection.FieldReflection.*;
import static reflection.MethodReflection.getMethod;
import static reflection.Utils.*;

public class Vars {

    //Unsafe
    public static Unsafe unsafe;

    //Class
    public static Method getDeclaredMethods0Method; //gets all constructors
    public static Method getDeclaredConstructors0Method; //gets all constructors
    public static Method getDeclaredFields0Method; //gets all fields
    public static Method getDeclaredClasses0Method; //gets all inner Classes

    //Constructor
    public static Method copyConstructorMethod; //adds root

    //Field
    public static Method copyFieldMethod; //adds root

    //AccessibleObject
    public static Field overrideField;

    //Method
    public static Method copyMethodMethod; //adds root

    //ClassLoader
    public static Method findLoadedClass0Method; //checks if a class is loaded

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);


            getDeclaredMethods0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class), true);
            getDeclaredConstructors0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class), true);
            getDeclaredFields0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class), true);
            getDeclaredClasses0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredClasses0"), true);


            copyConstructorMethod = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);


            copyFieldMethod = forceAccessible(Field.class.getDeclaredMethod("copy"), true);


            overrideField = forceAccessible(getField(AccessibleObject.class, "override"), true);


            copyMethodMethod = forceAccessible(Method.class.getDeclaredMethod("copy"), true);


            findLoadedClass0Method = forceAccessible(getMethod(ClassLoader.class, "findLoadedClass0", String.class), true);

        } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
