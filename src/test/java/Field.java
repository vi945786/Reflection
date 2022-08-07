import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reflection.FieldsReflation;
import vars.FakeClass;

import static org.junit.jupiter.api.Assertions.*;

public class Field {
    @Test
    public void setPrivateFinalField() throws Exception {
        FakeClass fakeClass = new FakeClass("");

        FieldsReflation.set("privateFinalField", fakeClass, 1);
        assertEquals(1, FieldsReflation.get("privateFinalField", fakeClass));
    }

    @Test
    public void setPrivateFinalFieldWrongType() {
        FakeClass fakeClass = new FakeClass("");

        Assertions.assertThrows(Exception.class, () -> FieldsReflation.set("privateFinalField", fakeClass, ""), "Types not compatible");
    }

    @Test
    public void setPrivateStaticFinalField() throws Exception {
        FieldsReflation.set("privateStaticFinalField", FakeClass.class, 1);
        assertEquals(1, FieldsReflation.get("privateStaticFinalField", FakeClass.class));
    }

    @Test
    public void setPrivateStaticFinalFieldWrongType() {
        Assertions.assertThrows(Exception.class, () -> FieldsReflation.set("privateStaticFinalFieldWrongType", FakeClass.class, ""), "Types not compatible");
    }
}
