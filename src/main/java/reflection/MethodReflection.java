package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static reflection.Utils.*;
import static reflection.Vars.*;

public class MethodReflection {

    /**
     * invokes the method passed in
     * @param m method to invoke
     * @param instance the instance of the object to invoke the method with (null if method is static)
     * @param args the arguments to invoke the method with
     * @return new instance of constructor
     */
    public static Object useMethod(Method m, Object instance, Object ... args) {
        try {
            if(args.length == m.getParameterCount()) {
                boolean isOverride = override.getBoolean(m);

                if(!isOverride) {
                    forceAccessible(m, true);
                }

                Object value = m.invoke(instance ,args);

                if(!isOverride) {
                    forceAccessible(m, false);
                }
                return value;
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets method from class and all superclasses
     * @param clazz class the method is in
     * @param name the name of the method
     * @param includeInheritedFields if to search in the superclasses
     * @param classes the argument types of the method
     * @return the constructor
     */
    public static Method getMethod(Class<?> clazz, String name, boolean includeInheritedFields, Class<?> ... classes) {
        for(Method method : getMethods(clazz, includeInheritedFields)) {
            if (method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), classes)) {
                return method;
            }
        }
        throw new NullPointerException("method doesn't exist");
    }

    /**
     * gets all methods from class and all superclasses if specified
     * @param clazz the class the method is in
     * @param includeInheritedFields if to get methods from superclasses
     * @return the methods specified
     */
    public static Method[] getMethods(Class<?> clazz, boolean includeInheritedFields) {
        try {
            List<Method> methods = new ArrayList<>();

            while (clazz != null) {
                for (Method method : (Method[]) getDeclaredMethods0.invoke(clazz, false)) {
                    methods.add((Method) copyMethod.invoke(method));
                }
                if(includeInheritedFields) {
                    clazz = clazz.getSuperclass();
                } else {
                    clazz = null;
                }
            }

            if(methods.isEmpty()) {
                throw new NullPointerException("no methods in class");
            }
            return methods.toArray(new Method[]{methods.get(0)});
        } catch (InvocationTargetException | IllegalAccessException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }
}
