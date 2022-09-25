package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static reflection.Utils.*;
import static reflection.Vars.*;

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
                boolean isOverride = overrideField.getBoolean(c);

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
