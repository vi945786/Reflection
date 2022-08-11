import org.junit.jupiter.api.Test;
import vars.FakeClass;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.FieldsReflection.getField;
import static reflection.Utils.changeModifiers;

public class UtilsTest {

    @Test
    public void setModifierFinal() {
    Field f = getField(FakeClass.class, "wasMethodUsed");
    changeModifiers(f, Modifier.FINAL);
    assertTrue(Modifier.isFinal(f.getModifiers()));
    }

    @Test
    public void setModifierVolatileStrict() {
        Field f = getField(FakeClass.class, "wasMethodUsed");
        changeModifiers(f, Modifier.VOLATILE, Modifier.STRICT);
        assertTrue(Modifier.isVolatile(f.getModifiers()) && Modifier.isStrict(f.getModifiers()));
    }
}
