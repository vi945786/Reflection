package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import static reflection.Boxing.unbox;
import static reflection.Boxing.wrapperToPrimitive;
import static reflection.Utils.forceAccessible;

public class MethodReflection {

    public static Object useMethod(Method m, Object instance, Object ... args) {
        try {
            if(args.length == m.getParameterCount()) {
                for(int i = 0;i < m.getParameterCount();i++) {
                    if(m.getParameterTypes()[i] != args[i].getClass() && m.getParameterTypes()[i].isPrimitive() && wrapperToPrimitive(args[i].getClass()) == m.getParameterTypes()[i]) {
                        switch (args[i].getClass().getTypeName()) {
                            case "byte" -> args[i] = unbox((Byte) args[i]);
                            case "short" -> args[i] = unbox((Short) args[i]);
                            case "int" -> args[i] = unbox((Integer) args[i]);
                            case "long" -> args[i] = unbox((Long) args[i]);
                            case "float" -> args[i] = unbox((Float) args[i]);
                            case "double" -> args[i] = unbox((Double) args[i]);
                            case "char" -> args[i] = unbox((Character) args[i]);
                            case "boolean" -> args[i] = unbox((Boolean) args[i]);
                        }
                    }
                }
                forceAccessible(m);
                return m.invoke(instance ,args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
