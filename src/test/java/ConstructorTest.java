import org.junit.jupiter.api.Test;
import reflection.ConstructorReflection;
import reflection.FieldsReflection;
import vars.FakeClass;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {
    @Test
    public void getPrivateConstructorWithout() {
        FakeClass fakeClass = (FakeClass) Objects.requireNonNull(ConstructorReflection.get(FakeClass.class));

        assertEquals(1, FieldsReflection.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWith() {
        FakeClass fakeClass = (FakeClass) Objects.requireNonNull(ConstructorReflection.get(FakeClass.class, 1));

        assertEquals(1, FieldsReflection.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWithTooMany() {
        FakeClass fakeClass = (FakeClass) ConstructorReflection.get(FakeClass.class, 1, 1);

        assertNull(FieldsReflection.get("privateFinalField", fakeClass));
    }

    @Test
    public void getPrivateConstructorWithWrongType() {
        FakeClass fakeClass = (FakeClass) ConstructorReflection.get(FakeClass.class, 1f);

        assertNull(FieldsReflection.get("privateFinalField", fakeClass));
    }
}
