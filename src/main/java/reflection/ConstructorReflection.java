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
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?> ... classes) {
        for(Constructor<?> constructor : getConstructors(clazz)) {
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
    public static Constructor<?>[] getConstructors(Class<?> clazz) {
        try {
            List<Constructor<?>> constructors = new ArrayList<>();

            for (Constructor<?> constructor : (Constructor<?>[]) getDeclaredConstructors0Method.invoke(clazz, false)) {
                constructors.add((Constructor<?>) copyConstructorMethod.invoke(constructor));
            }

            if(constructors.isEmpty()) {
                throw new NullPointerException("no constructors in class");
            }
            return constructors.toArray(new Constructor[]{constructors.get(0)});
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
