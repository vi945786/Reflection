package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import static reflection.Boxing.*;
import static reflection.Utils.*;

public class ConstructorReflection {

    public static Object useConstructor(Constructor c, Object ... args) {
        try {
            if(args.length == c.getParameterCount()) {
                for(int i = 0;i < c.getParameterCount();i++) {
                    if(c.getParameterTypes()[i] != args[i].getClass() && c.getParameterTypes()[i].isPrimitive() && wrapperToPrimitive(args[i].getClass()) == c.getParameterTypes()[i]) {
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
                forceAccessible(c);
                return c.newInstance(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Constructor getConstructor(Class<?> clazz, Class ... classes) {
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
