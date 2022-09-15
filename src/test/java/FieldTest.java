import org.junit.jupiter.api.Test;
import reflection.FieldReflection;
import sun.misc.Unsafe;
import vars.FakeClass;
import vars.FakeClass2;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;
import static reflection.FieldReflection.getFieldValue;
import static reflection.Utils.forceAccessible;

public class FieldTest {
    @Test
    public void setPrivateFinalField() {
        FakeClass fakeClass = new FakeClass("");

        FieldReflection.setFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass, 1, false);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldReflection.setFieldValue(getField(FakeClass.class, "privateStaticFinalField"), FakeClass.class, 1, true);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateStaticFinalField"), null));
    }

    @Test
    public void setPrivateFinalFieldSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();

        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateFinalField"), fakeClass2, 1, false);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateFinalField"), fakeClass2));
    }

    @Test
    public void setPrivateStaticFinalFieldSuperclass() {
        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass"), FakeClass2.class, 1, true);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass"), null));
    }
}
