package reflection;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Boxing {
    public static Class[] classes = {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class};
    public static Class[] primitives = {byte.class, short.class, int.class, long.class, float.class, double.class, char.class, boolean.class};
    public static Map<Class, Method> unboxing = new HashMap<>();

    static {
        try {
            Method m = Byte.class.getMethod("byteValue");
            m.setAccessible(true);
            unboxing.put(Byte.class, m);

            m = Short.class.getMethod("shortValue");
            m.setAccessible(true);
            unboxing.put(Short.class, m);

            m = Integer.class.getMethod("intValue");
            m.setAccessible(true);
            unboxing.put(Integer.class, m);

            m = Long.class.getMethod("longValue");
            m.setAccessible(true);
            unboxing.put(Long.class, m);

            m = Float.class.getMethod("floatValue");
            m.setAccessible(true);
            unboxing.put(Float.class, m);

            m = Double.class.getMethod("doubleValue");
            m.setAccessible(true);
            unboxing.put(Double.class, m);

            m = Character.class.getMethod("charValue");
            m.setAccessible(true);
            unboxing.put(Character.class, m);

            m = Boolean.class.getMethod("booleanValue");
            m.setAccessible(true);
            unboxing.put(Boolean.class, m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object box(Object o) {
        return isWrapper(o.getClass()) ? o : null;
    }

    public static byte unbox(Byte o) {
        return o.byteValue();
    }

    public static short unbox(Short o) {
        return o.shortValue();
    }

    public static int unbox(Integer o) {
        return o.intValue();
    }

    public static long unbox(Long o) {
        return o.longValue();
    }

    public static float unbox(Float o) {
        return o.floatValue();
    }

    public static double unbox(Double o) {
        return o.doubleValue();
    }

    public static char unbox(Character o) {
        return o.charValue();
    }

    public static boolean unbox(Boolean o) {
        return o.booleanValue();
    }

    public static boolean isWrapper(Class clazz) {
        return Arrays.stream(classes).toList().contains(clazz);
    }

    public static Class getWrapperType(Object o) {

        for(Class c : classes) {
            if(o.getClass() == c) {
                return c;
            }
        }
        return null;
    }

    public static Class getPrimitiveType(Object o) {

        for(int i = 0;i < 8; i++) {
            if(o.getClass() == classes[i]) {
                return primitives[i];
            }
        }
        return null;
    }

    public static Class wrapperToPrimitive(Class clazz) {

        for(int i = 0;i < 8; i++) {
            if(clazz == classes[i]) {
                return primitives[i];
            }
        }
        return null;
    }

    public static Class PrimitiveToWrapper(Class clazz) {

        for(int i = 0;i < 8; i++) {
            if(clazz == primitives[i]) {
                return classes[i];
            }
        }
        return null;
    }
}
