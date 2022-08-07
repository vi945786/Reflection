import org.junit.jupiter.api.Test;
import reflection.ConstructorReflation;
import reflection.FieldsReflation;
import vars.FakeClass;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

public class Constructor {
    @Test
    public void getPrivateConstructorWithout() {
        FakeClass fakeClass = (FakeClass) Objects.requireNonNull(ConstructorReflation.get(FakeClass.class));

        assertEquals(1, FieldsReflation.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWith() {
        FakeClass fakeClass = (FakeClass) Objects.requireNonNull(ConstructorReflation.get(FakeClass.class, 1));

        assertEquals(1, FieldsReflation.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWithTooMany() {
        FakeClass fakeClass = (FakeClass) ConstructorReflation.get(FakeClass.class, 1, 1);

        assertNull(FieldsReflation.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWithWrongType() {
        FakeClass fakeClass = (FakeClass) ConstructorReflation.get(FakeClass.class, 1f);

        assertNull(FieldsReflation.get("privateFinalField", fakeClass));
    }
}
