package reflection;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class FieldsReflection {

    public static void set(String fieldName, Object instance, Object value) throws Exception {
        try {
            Field f;
            if(instance instanceof Class<?>) {

                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Unsafe unsafe = (Unsafe) unsafeField.get(null);

                f = ((Class<?>) instance).getDeclaredField(fieldName);
                Object staticFieldBase = unsafe.staticFieldBase(f);
                long staticFieldOffset = unsafe.staticFieldOffset(f);

                if (f.getType() == byte.class) {
                    unsafe.putByte(staticFieldBase, staticFieldOffset, (Byte) value);
                } else if(f.getType() == short.class) {
                    unsafe.putShort(staticFieldBase, staticFieldOffset, (Short) value);
                } else if(f.getType() == int.class) {
                    unsafe.putInt(staticFieldBase, staticFieldOffset, (Integer) value);
                } else if(f.getType() == long.class) {
                    unsafe.putLong(staticFieldBase, staticFieldOffset, (Long) value);
                } else if(f.getType() == float.class) {
                    unsafe.putFloat(staticFieldBase, staticFieldOffset, (Float) value);
                } else if(f.getType() == double.class) {
                    unsafe.putDouble(staticFieldBase, staticFieldOffset, (Double) value);
                } else if(f.getType() == char.class) {
                    unsafe.putChar(staticFieldBase, staticFieldOffset, (Character) value);
                } else if(f.getType() == boolean.class) {
                    unsafe.putBoolean(staticFieldBase, staticFieldOffset, (Boolean) value);
                } else {
                    unsafe.putObject(unsafe.staticFieldBase(f), unsafe.staticFieldOffset(f), value);
                }

            } else {
                f = instance.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                f.set(instance, value);
            }
        } catch (Exception e) {
            throw new Exception("Types not compatible");
        }
    }

    public static Object get(String fieldName, Object instance) {
        try {
            Field f = instance instanceof Class<?> ? ((Class<?>) instance).getDeclaredField(fieldName) : instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
