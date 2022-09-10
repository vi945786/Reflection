package reflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Boxing {
    public static Class[] classes = {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class};
    public static Class[] primitives = {byte.class, short.class, int.class, long.class, float.class, double.class, char.class, boolean.class};

    /**
     * converts primitive type to wrapper
     * @param o primitive to convert
     * @return primitive type as wrapper
     */
    public static <T> T box(T o) {
        return isWrapper(o.getClass()) ? o : null;
    }

    /**
     * converts Byte wrapper to byte primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static byte unbox(Byte o) {
        return o.byteValue();
    }

    /**
     * converts Short wrapper to short primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static short unbox(Short o) {
        return o.shortValue();
    }

    /**
     * converts Integer wrapper to int primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static int unbox(Integer o) {
        return o.intValue();
    }

    /**
     * converts Long wrapper to long primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static long unbox(Long o) {
        return o.longValue();
    }

    /**
     * converts Float wrapper to float primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static float unbox(Float o) {
        return o.floatValue();
    }

    /**
     * converts Double wrapper to double primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static double unbox(Double o) {
        return o.doubleValue();
    }

    /**
     * converts Character wrapper to char primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static char unbox(Character o) {
        return o.charValue();
    }

    /**
     * converts Boolean wrapper to boolean primitive type
     * @param o wrapper to converts
     * @return wrapper as primitive type
     */
    public static boolean unbox(Boolean o) {
        return o.booleanValue();
    }

    /**
     * checks if class is a wrapper
     * @param clazz class to check
     * @return is it a wrapper
     */
    public static boolean isWrapper(Class clazz) {
        return Arrays.stream(classes).toList().contains(clazz);
    }

    /**
     * get the type of wrapper
     * @param clazz the class to check
     * @return the type of wrapper the class is
     */
    public static Class getWrapperType(Class clazz) {
        if(isWrapper(clazz)) {
            for(Class c : classes) {
                if(clazz == c) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * get the type of primitive
     * @param clazz the class to check
     * @return the type of primitive the class is
     */
    public static Class getPrimitiveType(Class clazz) {
        if(clazz.isPrimitive()) {
            for (int i = 0; i < 8; i++) {
                if (clazz == classes[i]) {
                    return primitives[i];
                }
            }
        }
        return null;
    }

    /**
     * converts wrapper class to primitive class
     * @param clazz class to converts
     * @return wrapper class as primitive class
     */
    public static Class wrapperToPrimitive(Class clazz) {
        if(isWrapper(clazz)) {
            for (int i = 0; i < 8; i++) {
                if (clazz == classes[i]) {
                    return primitives[i];
                }
            }
        }
        return null;
    }

    /**
     * converts primitive class to wrapper class
     * @param clazz class to converts
     * @return primitive class as wrapper class
     */
    public static Class primitiveToWrapper(Class clazz) {
        if(clazz.isPrimitive()) {
            for (int i = 0; i < 8; i++) {
                if (clazz == primitives[i]) {
                    return classes[i];
                }
            }
        }
        return null;
    }

    /**
     * gets the constructor of the wrapper passed in
     * @param clazz the wrapper to get the constructor of
     * @return the constructor of the wrapper passed in
     */
    public static Constructor getWrappersConstructors(Class clazz) {
        try {
            if(isWrapper(clazz)) {
                return clazz.getConstructor(wrapperToPrimitive(clazz));
            }
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
