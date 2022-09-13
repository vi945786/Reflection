package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static reflection.Utils.*;

public class ConstructorReflection {

    /**
     * makes a new instance of the constructor passed in
     * @param c constructor to create new instance of
     * @param args the arguments to make a new instance with
     * @return new instance of constructor
     */
    public static <T> T useConstructor(Constructor<T> c, Object ... args) {
        try {
            if(args.length == c.getParameterCount()) {
                boolean isOverride = override.getBoolean(c);

                if(!isOverride) {
                    forceAccessible(c, true);
                }

                T instance = c.newInstance(args);

                if(!isOverride) {
                    forceAccessible(c, false);
                }
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
        return null;
    }

    /**
     * gets all fields from class and all superclasses if specified
     * @param clazz the class the field is in
     * @return the fields specified
     */
    public static <T> Constructor<T>[] getConstructors(Class<T> clazz) {
        try {
            List<Constructor<?>> constructors = new ArrayList<>();

            Method m = Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class);
            m.setAccessible(true);

            for (Constructor<?> constructor : (Constructor<?>[]) m.invoke(clazz, false)) {

                Method copy = Constructor.class.getDeclaredMethod("copy");
                forceAccessible(copy, true);

                constructors.add((Constructor<?>) copy.invoke(constructor));
            }

            return constructors.toArray(new Constructor[]{constructors.get(0)});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
