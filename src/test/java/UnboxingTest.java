import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static reflection.Boxing.*;

public class UnboxingTest {

    @Test
    public void unboxByte() {
        try {
            assertEquals((byte) 1, unbox(new Byte((byte) 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxShort() {
        try {
            assertEquals((short) 1, unbox(new Short((short) 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxInt() {
        try {
            assertEquals(1, unbox(new Integer(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxLong() {
        try {
            assertEquals((long) 1, unbox(new Long(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxFloat() {
        try {
            assertEquals((float) 1, unbox(new Float(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxDouble() {
        try {
            assertEquals((double) 1, unbox(new Double(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxChar() {
        try {
            assertEquals('1', unbox(new Character('1')));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unboxBoolean() {
        try {
            assertEquals(true, unbox(new Boolean(true)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
