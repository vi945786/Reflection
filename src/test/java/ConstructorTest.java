import org.junit.jupiter.api.Test;
import vars.TestVar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reflection.ConstructorReflection.*;


public class ConstructorTest {

    @Test
    public void noConstructor() {
        TestVar testVar = createInstanceWithoutConstructor(TestVar.class);
        assertEquals(0, testVar.usedContructor);
    }

    @Test
    public void getPrivateConstructorInt() {
        TestVar testVar = null;
        try {
            testVar = useConstructor(getConstructor(TestVar.class, int.class), 1);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, testVar.usedContructor);
    }

    @Test
    public void getPrivateConstructor() {
        TestVar testVar = null;
        try {
            testVar = useConstructor(getConstructor(TestVar.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, testVar.usedContructor);
    }
}
