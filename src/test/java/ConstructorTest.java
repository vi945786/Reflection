import org.junit.jupiter.api.Test;
import vars.TestVar;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.ConstructorReflection.getConstructor;
import static reflection.ConstructorReflection.useConstructor;
import static reflection.FieldReflection.*;

public class ConstructorTest {
    @Test
    public void getPrivateConstructorInt() {
        TestVar testVar = (TestVar) useConstructor(getConstructor(TestVar.class, int.class), 1);
        assertEquals(1, getFieldValue(getField(TestVar.class, "privateFinalField", false), testVar));
    }

    @Test
    public void getPrivateConstructorInteger() {
        TestVar testVar = (TestVar) useConstructor(getConstructor(TestVar.class, Integer.class), Integer.valueOf(1));
        assertEquals(1, getFieldValue(getField(TestVar.class, "privateFinalField", false), testVar));
    }

    @Test
    public void getPrivateConstructor() {
        TestVar testVar = (TestVar) useConstructor(getConstructor(TestVar.class));
        assertEquals(1, getFieldValue(getField(TestVar.class, "privateFinalField", false), testVar));
    }
}
