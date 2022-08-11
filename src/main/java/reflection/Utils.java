package reflection;

import sun.misc.Unsafe;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Utils {

    public static Object forceAccessible(Object o) {
        try {
            Unsafe unsafe = getUnsafe();
            Field f = getField(AccessibleObject.class, "override");
            Method m = AccessibleObject.class.getDeclaredMethod("setAccessible0", boolean.class);

            unsafe.putBoolean(m, unsafe.objectFieldOffset(f), true);
            m.invoke(o, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Unsafe getUnsafe() {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class clazz, String fieldName) {
        try {
            Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            getDeclaredFields0.setAccessible(true);
            for(Field field : (Field[]) getDeclaredFields0.invoke(clazz, false)) {
                if(field.getName().equals(fieldName)) {
                    return field;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setStaticField(Field f, Object value) {
        Unsafe unsafe = getUnsafe();

        Object FieldBase = unsafe.staticFieldBase(f);
        long FieldOffset = unsafe.staticFieldOffset(f);
        if (f.getType() == byte.class) {
            unsafe.putByte(FieldBase, FieldOffset, (Byte) value);
        } else if(f.getType() == short.class) {
            unsafe.putShort(FieldBase, FieldOffset, (Short) value);
        } else if(f.getType() == int.class) {
            unsafe.putInt(FieldBase, FieldOffset, (Integer) value);
        } else if(f.getType() == long.class) {
            unsafe.putLong(FieldBase, FieldOffset, (Long) value);
        } else if(f.getType() == float.class) {
            unsafe.putFloat(FieldBase, FieldOffset, (Float) value);
        } else if(f.getType() == double.class) {
            unsafe.putDouble(FieldBase, FieldOffset, (Double) value);
        } else if(f.getType() == char.class) {
            unsafe.putChar(FieldBase, FieldOffset, (Character) value);
        } else if(f.getType() == boolean.class) {
            unsafe.putBoolean(FieldBase, FieldOffset, (Boolean) value);
        } else {
            unsafe.putObject(FieldBase, FieldOffset, value) ;
        }
    }
}
