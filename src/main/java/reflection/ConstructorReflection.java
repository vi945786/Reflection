package reflection;

import java.lang.reflect.Constructor;

public class ConstructorReflection {

    public static Object get(Class<?> clazz, Object ... args) {
        try {
            for (Constructor c : clazz.getDeclaredConstructors()) {
                Boolean isRight = false;
                c.setAccessible(true);
                if (c.getParameterCount() == args.length) {
                    isRight = true;
                    for (int i = 0; i < args.length; i++) {

                        if (args[i] instanceof Byte) {
                            clazz = byte.class;
                        } else if (args[i] instanceof Short) {
                            clazz = short.class;
                        } else if (args[i] instanceof Integer) {
                            clazz = int.class;
                        } else if (args[i] instanceof Long) {
                            clazz = long.class;
                        } else if (args[i] instanceof Float) {
                            clazz = float.class;
                        } else if (args[i] instanceof Double) {
                            clazz = double.class;
                        } else if (args[i] instanceof Character) {
                            clazz = char.class;
                        } else if (args[i] instanceof Boolean) {
                            clazz = boolean.class;
                        }

                        if (!clazz.isAssignableFrom(c.getParameterTypes()[i])) {
                            isRight = false;
                            break;
                        }
                    }
                }
                if (isRight) {
                    return c.newInstance(args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
