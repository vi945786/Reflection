package reflection;

import org.netbeans.lib.profiler.heap.Heap;
import org.netbeans.lib.profiler.heap.HeapFactory;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import static reflection.MethodReflection.getMethod;
import static reflection.MethodReflection.useMethod;
import static reflection.Vars.*;

public class ClassReflection {

    public static Class<?>[] getClassesFromMemory() {
        long pid = ProcessHandle.current().pid();
        File file = (File) useMethod(getMethod(File.class, "createTempFile", String.class, String.class), null, pid + "-", ".bin");
        file.delete();
        Heap heap;

        try {
            new Thread(() -> useMethod(getMethod(Runtime.class, "exec", String.class), Runtime.getRuntime(), "jmap -dump:all,format=b,file=" + file.getPath() + " " + pid)).start();
            while (!file.exists()) continue;
            heap = HeapFactory.createHeap(file);
            return heap.getAllClasses().stream().map(javaClass -> getClassByName(javaClass.getName())).filter(Objects::nonNull).toArray(Class[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            heap = null;
            System.gc();

            file.deleteOnExit();
        }

        return null;
    }

    /**
     * gets class from name
     * @param name the name of the class
     * @return the class
     */
    public static Class<?> getClassByName(String name) {
        try {
            return Class.forName(name);
        } catch (Throwable e) {

        }
        return null;
    }

    /**
     * gets class from name
     * @param name the name of the class
     * @param load if the method should load the class if it is not loaded
     * @return the class
     */
    public static Class<?> getClassByName(String name, boolean load) {
        try {
            if(load) {
                return getClassByName(name);
            }
            return (Class<?>) findLoadedClass0Method.invoke(null, name);
        } catch (Throwable e) {

        }
        return null;
    }

    /**
     * gets all inner classes
     * @param clazz the class the inner class is in
     * @return the inner class
     */
    public static Class<?>[] getInnerClasses(Class<?> clazz) {
        try {
            return (Class<?>[]) getDeclaredClasses0Method.invoke(clazz);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * filters all the classes implementing an interface or extending a class
     * @param implementingOrExtending the interface to class to check
     * @param classes the classes to filter
     * @return the classes implementing an interface or extending a class
     */
    public static Class<?>[] filterClassesAssignableFrom(Class<?> implementingOrExtending, Class<?> ... classes) {
        return Arrays.stream(classes).filter(implementingOrExtending::isAssignableFrom).toArray(Class[]::new);
    }
}
