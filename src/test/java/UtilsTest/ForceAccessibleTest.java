package UtilsTest;

import org.junit.jupiter.api.Test;
import vars.FakeClass;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.FieldReflection.getField;
import static reflection.Utils.*;

public class ForceAccessibleTest {

    @Test
    public void forceAccessibleField() throws IllegalAccessException {
        Field f = getField(Field.class, "clazz", false);
        assertFalse(override.getBoolean(f));
        forceAccessible(f, true);
        assertTrue(override.getBoolean(f));
        forceAccessible(f, false);
        assertFalse(override.getBoolean(f));
    }
}
