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

        FieldReflection.setFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldReflection.setFieldValue(getField(FakeClass.class, "privateStaticFinalField"), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateStaticFinalField"), null));
    }

    @Test
    public void setPrivateFinalFieldSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();

        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateFinalField"), fakeClass2, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateFinalField"), fakeClass2));
    }

    @Test
    public void setPrivateStaticFinalFieldSuperclass() {
        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass"), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass"), null));
    }

    @Test
    public void setPrivateStaticField() {
        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateStaticField"), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateStaticField"), null));
    }
}
