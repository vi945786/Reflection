package UtilsTest;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;
import static reflection.Utils.*;
import static reflection.Vars.*;

public class ForceAccessibleTest {

    @Test
    public void forceAccessibleField() throws IllegalAccessException {
        Field f = getField(Field.class, "clazz", false);
        assertFalse(overrideField.getBoolean(f));
        forceAccessible(f, true);
        assertTrue(overrideField.getBoolean(f));
        forceAccessible(f, false);
        assertFalse(overrideField.getBoolean(f));
    }
}
