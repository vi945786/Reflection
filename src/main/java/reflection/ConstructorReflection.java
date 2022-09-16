package reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static reflection.Utils.*;

public class ConstructorReflection {

    //---getFields---
    //Class.class
    public static Method getDeclaredConstructors0; //gets all constructors

    //Constructor.class
    public static Method copy; //adds root

    static {
        try {
            getDeclaredConstructors0 = forceAccessible(Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class), true);

            copy = forceAccessible(Constructor.class.getDeclaredMethod("copy"), true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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

            for (Constructor<?> constructor : (Constructor<?>[]) getDeclaredConstructors0.invoke(clazz, false)) {
                constructors.add((Constructor<?>) copy.invoke(constructor));
            }

            return constructors.toArray(new Constructor[]{constructors.get(0)});
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("no constructors in class");
    }
}
