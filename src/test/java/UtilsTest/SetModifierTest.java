package UtilsTest;

import org.junit.jupiter.api.Test;
import vars.FakeClass;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static reflection.FieldReflection.getField;
import static reflection.Utils.changeModifiers;

public class SetModifierTest {

    @Test
    public void setModifierFinal() {
    Field f = getField(FakeClass.class, "wasMethodUsed");
    assertFalse(Modifier.isFinal(f.getModifiers()));
    changeModifiers(f, Modifier.FINAL);
    assertTrue(Modifier.isFinal(f.getModifiers()));
    }

    @Test
    public void setModifierVolatileStrict() {
        Field f = getField(FakeClass.class, "wasMethodUsed");
        assertFalse(Modifier.isVolatile(f.getModifiers()) && Modifier.isStrict(f.getModifiers()));
        changeModifiers(f, Modifier.VOLATILE, Modifier.STRICT);
        assertTrue(Modifier.isVolatile(f.getModifiers()) && Modifier.isStrict(f.getModifiers()));
    }


    @Test
    public void setModifierNotFinalStrict() {
        Field f = getField(FakeClass.class, "wasMethodUsed");
        assertFalse(Modifier.isFinal(f.getModifiers()));
        changeModifiers(f, Modifier.FINAL);
        assertFalse(!Modifier.isFinal(f.getModifiers()) && Modifier.isStrict(f.getModifiers()));
        changeModifiers(f, ~Modifier.FINAL, Modifier.STRICT);
        assertTrue(!Modifier.isFinal(f.getModifiers()) && Modifier.isStrict(f.getModifiers()));
    }
    @Test
    public void setModifierNotPublic() {
        Field f = getField(FakeClass.class, "wasMethodUsed");
        assertTrue(Modifier.isPublic(f.getModifiers()));
        changeModifiers(f, ~Modifier.PUBLIC);
        assertFalse(Modifier.isPublic(f.getModifiers()));
    }
}
