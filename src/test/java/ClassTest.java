import org.junit.jupiter.api.Test;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.ClassReflection.*;

public class ClassTest {

    @Test
    public void getTestVar() {
        assertNotNull(getClassByName("vars.TestVar"));
    }

    @Test
    public void getInner() {
        assertNotNull(getInnerClasses(getClassByName("vars.TestVar")));
    }

    @Test
    public void filter() {
        Class<?>[] classes = filterClassesAssignableFrom(AccessibleObject.class, Field.class, ClassTest.class);
        assertTrue(classes.length == 1 && classes[0].getName().equals(Field.class.getName()));

        classes = filterClassesAssignableFrom(Serializable.class, Number.class, ClassTest.class);
        assertTrue(classes.length == 1 && classes[0].getName().equals(Number.class.getName()));
    }
}
