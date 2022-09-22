package reflection;

import sun.misc.Unsafe;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static reflection.ClassReflection.*;
import static reflection.Utils.forceAccessible;

public class Vars {

    //Unsafe.class
    public static Unsafe unsafe;

    //Class.class
    public static Method getDeclaredConstructors0Method; //gets all constructors
    public static Method getDeclaredFields0Method; //gets all fields
    public static Method getDeclaredMethods0Method; //gets all constructors
    public static Method getDeclaredClasses0Method; //gets all inner Classes

    //Constructor.class
    public static Method copyConstructorMethod; //adds root

    //Field.class
    public static Method copyFieldMethod; //adds root
    public static Field overrideFieldAccessorField;
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

    //ClassLoader.class
    public static Field classesField;

    //not class
    public static File bootstrapFileDir;
    public static Class<?>[] bootstrapClasses;

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
                getDeclaredClasses0Method = forceAccessible(Class.class.getDeclaredMethod("getDeclaredClasses0"), true);
            }

            {
                copyConstructorMethod = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);
            }

            {
                copyFieldMethod = forceAccessible(Field.class.getDeclaredMethod("copy"), true);
                overrideFieldAccessorField = forceAccessible((Field) copyFieldMethod.invoke(((Field[]) getDeclaredFields0Method.invoke(Field.class, false))[10]), true);
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

            {
                classesField = forceAccessible(((Field[]) getDeclaredFields0Method.invoke(ClassLoader.class, false))[7], true);
            }

            {
                bootstrapFileDir = new File(System.getProperty("sun.boot.library.path").substring(0, System.getProperty("sun.boot.library.path").lastIndexOf("\\")) + "\\jmods");
                {
                    Set<Class<?>> classes = new HashSet<>();
                    File[] files = bootstrapFileDir.listFiles();

                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];

                        ZipFile zipFile = new ZipFile(file.getPath());
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();

                        classes.addAll(Collections.list(entries).stream().filter(n -> n.getName().endsWith(".class") && !n.getName().contains("module-info")).map(ZipEntry::getName).map(n -> getClassByName(n.substring(0, n.lastIndexOf("/")).replace("/", ".").replace("classes.", ""), n.substring(n.lastIndexOf("/") + 1, n.length() - 6))).toList());
                        classes.removeAll(Collections.singleton(null));
                    }
                    bootstrapClasses = classes.toArray(Class[]::new);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }
    }
}
