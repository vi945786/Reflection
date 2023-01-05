package UtilsTest;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;
import static reflection.Utils.*;

public class ForceAccessibleTest {

    @Test
    public void forceAccessibleField() {
        Field f = getField(Field.class, "clazz");
        assertFalse(isAccessible(f));
        forceAccessible(f, true);
        assertTrue(isAccessible(f));
        forceAccessible(f, false);
        assertFalse(isAccessible(f));
    }
}
