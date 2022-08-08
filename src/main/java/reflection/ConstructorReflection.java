package reflection;

import java.lang.reflect.Constructor;
import static reflection.Boxing.*;

public class ConstructorReflection {

    public static Object get(Class<?> clazz, Object ... args) {
        try {
            Class[] classes = new Class[args.length];

            for(int i = 0; i < args.length; i++) {
                classes[i] = getPrimitiveType(args[i]);
            }

            for (Constructor c : clazz.getDeclaredConstructors()) {
                Boolean isRight = false;
                c.setAccessible(true);
                if (c.getParameterCount() == args.length) {
                    isRight = true;
                    for (int i = 0; i < args.length; i++) {

                        if (!classes[i].isAssignableFrom(c.getParameterTypes()[i])) {
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
