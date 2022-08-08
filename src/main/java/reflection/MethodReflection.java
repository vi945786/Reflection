package reflection;

import java.lang.reflect.Method;

import static reflection.Boxing.getPrimitiveType;

public class MethodReflection {

    public static Object get(String name, Object instance, Object ... args) {
        try {
            Class[] classes = new Class[args.length];

            for(int i = 0; i < args.length; i++) {
                classes[i] = getPrimitiveType(args[i]);
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
