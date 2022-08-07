import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static reflection.Boxing.*;

public class BoxingTest {
    @Test
    public void boxByte() {
        try {
            assertEquals(new Byte((byte) 1), box((byte) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxShort() {
        try {
            assertEquals(new Short((short) 1), box((short) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxInt() {
        try {
            assertEquals(new Integer(1), box((int) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxLong() {
        try {
            assertEquals(new Long(1), box((long) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxFloat() {
        try {
            assertEquals(new Float(1), box((float) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxDouble() {
        try {
            assertEquals(new Double(1), box((double) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxChar() {
        try {
            assertEquals(new Character((char) '1'), box((char) '1'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxBoolean() {
        try {
            assertEquals(new Boolean(true), box(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
