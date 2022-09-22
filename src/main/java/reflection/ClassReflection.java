package reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import static reflection.Vars.*;

public class ClassReflection {

    /**
     * gets class from name
     * @param packageName the package the class is in
     * @param name the name of the class
     * @return the class
     */
    public static Class<?> getClassByName(String packageName, String name) {
        try {
            return Class.forName(packageName + (packageName.equals("") ? "" : '.') + name, false, ClassReflection.class.getClassLoader());
        } catch (Throwable e) {}
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
        return Arrays.stream(classes).filter(c -> implementingOrExtending.isAssignableFrom(c)).toArray(Class[]::new);
    }
}
