package reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodReflection {

    public static Method getMethod(String name, Class clazz, Class ... classes) {
        try {
            Method m = Class.class.getDeclaredMethod("getDeclaredMethods0", boolean.class);
            m.setAccessible(true);
            for(Method method : (Method[]) m.invoke(clazz, false)) {
                if(method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), classes)) {
                    return method;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
