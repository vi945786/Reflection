package UtilsTest;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.FieldReflection.getField;
import static reflection.Utils.forceAccessible;

public class ForceAccessibleTest {

    @Test
    public void forceAccessibleField() {
        Field f = getField("clazz", Field.class);
        assertFalse(f.canAccess(f));
        forceAccessible(f);
        assertTrue(f.canAccess(f));
    }
}
