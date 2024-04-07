import org.junit.jupiter.api.Test;
import reflection.FieldReflection;
import vars.TestVar;
import vars.TestVar2;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldReflection.*;

public class FieldTest {

    @Test
    public void setPublicFinalInt() {
        TestVar testVar = new TestVar();

        try {
            setFieldValue(getField(TestVar.class, "publicFinalInt"), testVar, 1);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, testVar.publicFinalInt);
    }

    @Test
    public void setPrivateStaticLong() {
        try {
            setStaticFieldValue(getField(TestVar.class, "privateStaticLong"), 1L);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, TestVar.getPrivateStaticLong());
    }

    @Test
    public void setPrivateVolatileFloat() {
        TestVar testVar = new TestVar();

        try {
            setFieldValue(getField(TestVar.class, "privateVolatileFloat"), testVar, 1F);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, testVar.getPrivateVolatileFloat());
    }

    @Test
    public void setPublicFinalObjectSuperclass() {
        TestVar2 testVar2 = new TestVar2();

        try {
            setFieldValue(getField(TestVar2.class, "publicFinalObject", true), testVar2, testVar2);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        assertEquals(testVar2, testVar2.publicFinalObject);
    }
}
