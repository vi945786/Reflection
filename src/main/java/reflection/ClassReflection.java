package reflection;

public class ClassReflection {

    public static Class<?> getClass(String packageName, String name) {
        try {
            return Class.forName(packageName + '.' + name);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getInnerClass(Class<?> clazz, String name) {
        try {
            return Class.forName(clazz.getName() + '$' + name);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
