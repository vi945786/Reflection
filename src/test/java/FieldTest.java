import org.junit.jupiter.api.Test;
import reflection.FieldReflection;
import vars.TestVar;
import vars.TestVar2;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.getField;

public class FieldTest {
    @Test
    public void setPrivateFinalField() {
        TestVar testVar = new TestVar("");

        FieldReflection.setFieldValue(getField(TestVar.class, "privateFinalField", false), testVar, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar.class, "privateFinalField", false), testVar));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldReflection.setFieldValue(getField(TestVar.class, "privateStaticFinalField", false), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar.class, "privateStaticFinalField", false), null));
    }

    @Test
    public void setPrivateStaticField() {
        FieldReflection.setFieldValue(getField(TestVar.class, "privateStaticField", false), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar.class, "privateStaticField", false), null));
    }

    @Test
    public void setPrivateVolatileField() {
        TestVar testVar = new TestVar("");

        FieldReflection.setFieldValue(getField(TestVar.class, "privateVolatileField", false), testVar, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar.class, "privateVolatileField", false), testVar));
    }

    @Test
    public void setPrivateFinalFieldSuperclass() {
        TestVar2 fakeClass2 = new TestVar2();

        FieldReflection.setFieldValue(getField(TestVar2.class, "privateFinalField", true), fakeClass2, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar2.class, "privateFinalField", true), fakeClass2));
    }

    @Test
    public void setPrivateStaticFinalFieldSuperclass() {
        FieldReflection.setFieldValue(getField(TestVar2.class, "privateStaticFinalFieldSuperclass", true), null, 1);
        assertEquals(1, FieldReflection.getFieldValue(getField(TestVar2.class, "privateStaticFinalFieldSuperclass", true), null));
    }
}
