import org.junit.jupiter.api.Test;
import reflection.FieldsReflection;
import vars.FakeClass;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldsReflection.getField;
import static reflection.Utils.changeModifiers;

public class FieldTest {
    @Test
    public void setPrivateFinalField() {
        FakeClass fakeClass = new FakeClass("");

        FieldsReflection.setFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass, 1);
        assertEquals(1, FieldsReflection.getFieldValue(getField(FakeClass.class, "privateFinalField"), fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() {
        FieldsReflection.setFieldValue(getField(FakeClass.class, "privateStaticFinalField"), FakeClass.class, 1);
        assertEquals(1, FieldsReflection.getFieldValue(getField(FakeClass.class, "privateStaticFinalField"), null));
        Field f = getField(FakeClass.class, "wasMethodUsed");
        changeModifiers(f, Modifier.FINAL);
        Modifier.isFinal(f.getModifiers());
    }
}
