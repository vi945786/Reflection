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

        FieldReflection.setFieldValue(getField(FakeClass.class, "privateFinalField", false), fakeClass, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateFinalField", false), fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldReflection.setFieldValue(getField(FakeClass.class, "privateStaticFinalField", false), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateStaticFinalField", false), null));
    }

    @Test
    public void setPrivateStaticField() {
        FieldReflection.setFieldValue(getField(FakeClass.class, "privateStaticField", false), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateStaticField", false), null));
    }

    @Test
    public void setPrivateVolatileField() {
        FakeClass fakeClass = new FakeClass("");

        FieldReflection.setFieldValue(getField(FakeClass.class, "privateVolatileField", false), fakeClass, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateVolatileField", false), fakeClass));
    }

    @Test
    public void setPrivateStaticVolatileField() {
        FieldReflection.setFieldValue(getField(FakeClass.class, "privateStaticVolatileField", false), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass.class, "privateStaticVolatileField", false), null));
    }

    @Test
    public void setPrivateFinalFieldSuperclass() {
        FakeClass2 fakeClass2 = new FakeClass2();

        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateFinalField", true), fakeClass2, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateFinalField", true), fakeClass2));
    }

    @Test
    public void setPrivateStaticFinalFieldSuperclass() {
        FieldReflection.setFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass", true), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(FakeClass2.class, "privateStaticFinalFieldSuperclass", true), null));
    }
}
