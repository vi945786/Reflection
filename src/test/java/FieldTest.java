import org.junit.jupiter.api.Test;
import reflection.FieldReflection;
import vars.TestVar;
import vars.TestVar2;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.*;

public class FieldTest {

    @Test
    public void setPublicFinalField() {
        TestVar testVar = new TestVar("");

        FieldReflection.setFieldValue(getField(TestVar.class, "publicFinalField", false), testVar, 1);
        assertEquals(1, testVar.publicFinalField);
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
    public void setPublicFinalFieldSuperclass() {
        TestVar2 testVar2 = new TestVar2();

        FieldReflection.setFieldValue(getField(TestVar2.class, "publicFinalField", true), testVar2, 1);
        assertEquals(1, testVar2.publicFinalField);
    }
}
