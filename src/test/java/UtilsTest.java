import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;
import static reflection.ReflectionUtils.*;

public class UtilsTest {

    @Test
    public void forceAccessibleField() {
        Field f = null;
        try {
            f = getField(Field.class, "clazz");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        assertFalse(isAccessible(f));
        forceAccessible(f, true);
        assertTrue(isAccessible(f));
        forceAccessible(f, false);
        assertFalse(isAccessible(f));
    }
}
