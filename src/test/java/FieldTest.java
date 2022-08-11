import org.junit.jupiter.api.Test;
import reflection.FieldsReflection;
import vars.FakeClass;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldsReflection.getField;

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
    }
}
