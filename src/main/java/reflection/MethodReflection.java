package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import static reflection.Utils.*;

public class MethodReflection {

    /**
     * invokes the method passed in
     * @param m method to invoke
     * @param instance the instance of the object to invoke the method with (null if field is static)
     * @param args the arguments to invoke the method with
     * @return new instance of constructor
     */
    public static Object useMethod(Method m, Object instance, Object ... args) {
        try {
            if(args.length == m.getParameterCount()) {
                forceAccessible(m);
                return m.invoke(instance ,args);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets method from class and all superclasses
     * @param name the name of the method
     * @param clazz class the method is in
     * @param classes the argument types of the method
     * @return the constructor
     */
    public static Method getMethod(String name, Class<?> clazz, Class ... classes) {
        try {
            Method m = null;
            try {
                m = clazz.getMethod(name, classes);
                if(m != null) {
                    return m;
                }
            } catch (NoSuchMethodException e) {

            }

            if(m == null) {
                Class superClass = clazz;
                while (m == null && superClass.getSuperclass() != null) {
                    try {
                        m = superClass.getDeclaredMethod(name, classes);
                        return m;
                    } catch (NoSuchMethodException e) {
                        superClass = superClass.getSuperclass();
                    }
                }
            }

            if (m == null) {
                m = Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class);
                m.setAccessible(true);
                for (Method method : (Method[]) m.invoke(clazz, false)) {
                    if (method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), classes)) {
                        m = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                        m.setAccessible(true);

                        Method copy = Method.class.getDeclaredMethod("copy");
                        forceAccessible(copy);

                        return (Method) copy.invoke(method);
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
