package UtilsTest;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reflection.FieldsReflection.getField;
import static reflection.Utils.forceAccessible;

public class forceAccessible {

    @Test
    public void forceAccessibleField() {
        Field f = getField(Field.class, "clazz");
        forceAccessible(f);
        assertTrue(f.isAccessible());
    }
}
