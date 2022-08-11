import org.junit.jupiter.api.Test;
import reflection.FieldsReflection;
import vars.FakeClass;
import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    @Test
    public void setPrivateFinalField() throws Exception {
        FakeClass fakeClass = new FakeClass("");

        FieldsReflection.setFieldValue("privateFinalField", fakeClass, 1);
        assertEquals(1, FieldsReflection.getFieldValue("privateFinalField", fakeClass));
    }

    @Test
    public void setPrivateStaticFinalField() throws Exception {
        FieldsReflection.setFieldValue("privateStaticFinalField", FakeClass.class, 1);
        assertEquals(1, FieldsReflection.getFieldValue("privateStaticFinalField", FakeClass.class));
    }
}
