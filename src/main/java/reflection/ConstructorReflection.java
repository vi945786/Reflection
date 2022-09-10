package reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import static reflection.Utils.*;

public class ConstructorReflection {

    /**
     * makes a new instance of the constructor passed in
     * @param c constructor to create new instance of
     * @param args the arguments to make a new instance with
     * @return new instance of constructor
     */
    public static Object useConstructor(Constructor c, Object ... args) {
        try {
            if(args.length == c.getParameterCount()) {
                forceAccessible(c);
                return c.newInstance(args);
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
    public static Constructor getConstructor(Class<?> clazz, Class ... classes)  {
        try {
            Constructor c = null;
            try {
                c = clazz.getConstructor(classes);
                if (c != null) {
                    return c;
                }
            } catch (NoSuchMethodException e) {

            }

            try {
                if (c == null) {
                    c = clazz.getDeclaredConstructor(classes);
                    if (c != null) {
                        return c;
                    }
                }
            } catch (NoSuchMethodException e) {

            }

            if (c == null) {
                Method m = Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class);
                m.setAccessible(true);
                for (Constructor constructor : (Constructor[]) m.invoke(clazz, false)) {
                    if (Arrays.equals(constructor.getParameterTypes(), classes)) {
                        m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                        m.setAccessible(true);

                        Method copy = Constructor.class.getDeclaredMethod("copy");
                        forceAccessible(copy);

                        return (Constructor) copy.invoke(constructor);
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
