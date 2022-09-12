import org.junit.jupiter.api.Test;
import reflection.FieldReflection;
import vars.FakeClass;
import vars.FakeClass2;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;

public class FieldTest {
    @Test
    public void setPrivateFinalField() {
        FakeClass fakeClass = new FakeClass("");

        FieldReflection.setFieldValue(getField("privateFinalField", FakeClass.class), fakeClass, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField("privateFinalField", FakeClass.class), fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldReflection.setFieldValue(getField("privateStaticFinalField", FakeClass.class), FakeClass.class, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField("privateStaticFinalField", FakeClass.class), null));
    }

    @Test
    public void setPrivateFinalFieldSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();

        FieldReflection.setFieldValue(getField("privateFinalField", FakeClass2.class), fakeClass2, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField("privateFinalField", FakeClass2.class), fakeClass2));
    }

    @Test
    public void setPrivateStaticFinalFieldSuperclass() {
        FieldReflection.setFieldValue(getField("privateStaticFinalFieldSuperclass", FakeClass2.class), FakeClass2.class, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField("privateStaticFinalFieldSuperclass", FakeClass2.class), null));
    }
}
