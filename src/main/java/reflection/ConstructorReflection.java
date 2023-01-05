package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static reflection.Utils.*;
import static reflection.Vars.*;

public class ConstructorReflection {

    /**
     * creates an instance of a class using the default constructor (even when the default constructor does not exist)
     * @param clazz the class to make an instance of
     * @return an instance of the class
     */
    public static <T> T createInstanceWithoutConstructor(Class<T> clazz) {
        try {
            return (T) unsafe.allocateInstance(clazz);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * makes a new instance of the constructor passed in
     * @param c constructor to create new instance of
     * @param args the arguments to make a new instance with
     * @return new instance of constructor
     */
    public static <T> T useConstructor(Constructor<T> c, Object ... args) {
        try {
            if(args.length == c.getParameterCount()) {
                boolean isOverride = isAccessible(c);

                forceAccessible(c, true);

                T instance = c.newInstance(args);

                forceAccessible(c, isOverride);

                return instance;
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets constructor
     * @param clazz class to get constructor of
     * @param classes the argument types of the constructor
     * @return the constructor
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?> ... classes) {
        for(Constructor<T> constructor : getConstructors(clazz)) {
            if (Arrays.equals(constructor.getParameterTypes(), classes)) {
                return constructor;
            }
        }
        throw new NullPointerException("constructor doesn't exist");
    }

    /**
     * gets all fields from class and all superclasses if specified
     * @param clazz the class the field is in
     * @return the fields specified
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> clazz) {
        try {
            List<Constructor<T>> constructors = new ArrayList<>();

            for (Constructor<T> constructor : (Constructor<T>[]) getDeclaredConstructors0Method.invoke(clazz, false)) {
                constructors.add((Constructor<T>) copyConstructorMethod.invoke(constructor));
            }

            return constructors.toArray(new Constructor[0]);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Constructor[0];
    }
}
