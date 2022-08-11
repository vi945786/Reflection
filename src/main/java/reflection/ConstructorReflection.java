package reflection;

import java.lang.reflect.Constructor;
import static reflection.Boxing.*;
import static reflection.Utils.forceAccessible;

public class ConstructorReflection {

    public static Object get(Class<?> clazz, Object ... args) {
        try {
            Class[] classes = new Class[args.length];

            for(int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
                if(isWrapper(args[i].getClass())) {
                    classes[i] = getPrimitiveType(args[i]);
                }
            }

            Constructor c = clazz.getDeclaredConstructor(classes);
            forceAccessible(c);
            return c.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
