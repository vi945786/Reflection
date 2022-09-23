package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.*;
import static reflection.FieldReflection.getField;
import static reflection.Utils.*;

public class Vars {

    //Unsafe.class
    public static Unsafe unsafe;

    //Class.class
    public static Method getDeclaredMethods0Method; //gets all constructors
    public static Method getDeclaredConstructors0Method; //gets all constructors
    public static Method getDeclaredFields0Method; //gets all fields
    public static Method getDeclaredClasses0Method; //gets all inner Classes

    //Constructor.class
    public static Method copyConstructorMethod; //adds root

    //Field.class
    public static Method copyFieldMethod; //adds root
    public static Field overrideFieldAccessorField;
    public static Field fieldAccessorField;
    public static Field overrideField;
    public static Field trustedFinalField;
    public static Field rootField;

    //Method.class
    public static Method copyMethodMethod; //adds root

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

    //ClassLoader.class
    public static Field classesField;

    //other
    public static int javaVersion;

    //java 18 and later
    //MethodHandleFieldAccessorImpl.class
    public static Class<?> methodHandleFieldAccessorImplClass;
    public static Field fieldFlagsField;
    public static Field setterField;

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
                overrideFieldAccessorField = forceAccessible(getField(Field.class, "overrideFieldAccessor", false), true);
                fieldAccessorField = forceAccessible(getField(Field.class, "fieldAccessor", false), true);
                overrideField = forceAccessible(getField(AccessibleObject.class, "override", false), true);
                rootField = forceAccessible(getField(Field.class, "root", false), true);
            }

            {
                copyMethodMethod = forceAccessible(Method.class.getDeclaredMethod("copy"), true);
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

            {
                classesField = forceAccessible((Field) copyFieldMethod.invoke(((Field[]) getDeclaredFields0Method.invoke(ClassLoader.class, false))[7]), true);
            }

            {
                javaVersion = Integer.parseInt(System.getProperties().getProperty("java.specification.version"));
            }

            if(javaVersion >= 18) {
                methodHandleFieldAccessorImplClass = Class.forName("jdk.internal.reflect.MethodHandleFieldAccessorImpl");
                fieldFlagsField = forceAccessible(methodHandleFieldAccessorImplClass.getDeclaredField("fieldFlags"), true);
                setterField = forceAccessible(methodHandleFieldAccessorImplClass.getDeclaredField("setter"), true);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
