package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConstructorReflection {

    public static Object getConstructor(Class<?> clazz, Class ... classes) {
        try {
            Method m = Class.class.getDeclaredMethod("getDeclaredConstructors0", boolean.class);
            m.setAccessible(true);
            for(Constructor constructor : (Constructor[]) m.invoke(clazz, false)) {
                if(Arrays.equals(constructor.getParameterTypes(), classes)) {
                    return constructor;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
