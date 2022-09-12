package Boxing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static reflection.Boxing.box;

public class BoxingTest {
    @Test
    public void boxByte() {
        try {
            assertEquals(Byte.valueOf((byte) 1), box((byte) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxShort() {
        try {
            assertEquals(Short.valueOf((short) 1), box((short) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxInt() {
        try {
            assertEquals(Integer.valueOf(1), box(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxLong() {
        try {
            assertEquals(Long.valueOf(1), box((long) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxFloat() {
        try {
            assertEquals(Float.valueOf((1)), box((float) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxDouble() {
        try {
            assertEquals(Double.valueOf((1)), box((double) 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxChar() {
        try {
            assertEquals(Character.valueOf(('1')), box('1'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxBoolean() {
        try {
            assertEquals(Boolean.TRUE, box(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
