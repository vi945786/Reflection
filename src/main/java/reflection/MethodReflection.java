package reflection;

import java.lang.reflect.Method;
import static reflection.Boxing.getPrimitiveType;
import static reflection.Boxing.isWrapper;
import static reflection.Utils.*;

public class MethodReflection {

    public static Object get(String name, Object instance, Object ... args) {
        try {
            Class[] classes = new Class[args.length];

            for(int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
                if(isWrapper(args[i].getClass())) {
                    classes[i] = getPrimitiveType(args[i]);
                }
            }

            Method m = instance.getClass().getDeclaredMethod(name, classes);
            forceAccessible(m);
            return m.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
