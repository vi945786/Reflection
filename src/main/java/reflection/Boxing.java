package reflection;

import java.lang.reflect.Method;

public class Boxing {
    public static Object box(Object o) {
        return o;
    }


    public static Method unbox(Object o) {
        try {
            Class[] classes = new Class[]{Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class};
            String[] methodName = new String[]{"byteValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue", "charValue", "booleanValue"};
            Method m;
            for(int i = 0;i < 8;i++) {
                if(o.getClass() == classes[i]) {
                    m = o.getClass().getDeclaredMethod(methodName[i]);
                    m.setAccessible(true);
                    return m;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
