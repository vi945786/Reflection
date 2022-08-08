import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reflection.FieldsReflection;
import vars.FakeClass;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    @Test
    public void setPrivateFinalField() throws Exception {
        FakeClass fakeClass = new FakeClass("");

        FieldsReflection.set("privateFinalField", fakeClass, 1);
        assertEquals(1, FieldsReflection.get("privateFinalField", fakeClass));
    }

    @Test
    public void setPrivateFinalFieldWrongType() {
        FakeClass fakeClass = new FakeClass("");

        Assertions.assertThrows(Exception.class, () -> FieldsReflection.set("privateFinalField", fakeClass, ""), "Types not compatible");
    }

    @Test
    public void setPrivateStaticFinalField() throws Exception {
        FieldsReflection.set("privateStaticFinalField", FakeClass.class, 1);
        assertEquals(1, FieldsReflection.get("privateStaticFinalField", FakeClass.class));
    }

    @Test
    public void setPrivateStaticFinalFieldWrongType() {
        Assertions.assertThrows(Exception.class, () -> FieldsReflection.set("privateStaticFinalFieldWrongType", FakeClass.class, ""), "Types not compatible");
    }
}
