import org.junit.jupiter.api.Test;
import reflection.ClassReflection;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reflection.ClassReflection.getInnerClass;

public class ClassTest {

    @Test
    public void getMethodHandles() {
        assertNotNull(ClassReflection.getClass("java.lang.invoke", "MethodHandles"));
    }

    @Test
    public void getMethodHandles$Lookup() {
        assertNotNull(getInnerClass(ClassReflection.getClass("java.lang.invoke", "MethodHandles"), "Lookup"));
    }
}
