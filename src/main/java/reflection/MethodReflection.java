package reflection;

import java.lang.reflect.Method;

public class MethodReflection {

    public static Object get(String name, Object instance, Object ... args) {
        try {
            Class[] classes = new Class[args.length];

            for(int i = 0; i < args.length; i++) {
                if (args[i] instanceof Byte) {
                    classes[i] = byte.class;
                } else if(args[i] instanceof Short) {
                    classes[i] = short.class;
                } else if(args[i] instanceof Integer) {
                    classes[i] = int.class;
                } else if(args[i] instanceof Long) {
                    classes[i] = long.class;
                } else if(args[i] instanceof Float) {
                    classes[i] = float.class;
                } else if(args[i] instanceof Double) {
                    classes[i] = double.class;
                } else if(args[i] instanceof Character) {
                    classes[i] = char.class;
                } else if(args[i] instanceof Boolean) {
                    classes[i] = boolean.class;
                }
            }

            for (Method m : (instance instanceof Class<?> ? ((Class<?>) instance) : instance.getClass()).getDeclaredMethods()) {
                Boolean isRight = false;
                m.setAccessible(true);
                if (m.getName().equals(name) && m.getParameterCount() == args.length) {
                    isRight = true;
                    for (int i = 0; i < args.length; i++) {

                        if (!classes[i].isAssignableFrom(m.getParameterTypes()[i])) {
                            isRight = false;
                            break;
                        }
                    }
                }
                if (isRight) {
                    return m.invoke(instance, args);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
