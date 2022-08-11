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
    public void setPrivateStaticFinalField() throws Exception {
        FieldsReflection.set("privateStaticFinalField", FakeClass.class, 1);
        assertEquals(1, FieldsReflection.get("privateStaticFinalField", FakeClass.class));
    }
}
