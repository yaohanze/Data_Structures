import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    /** Performs a few arbitrary tests to see if the isSameNumber method is correct */

    @Test
    public void testisSameNumber() {
        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(200, 200));
        assertTrue(!Flik.isSameNumber(3, 5));
        assertTrue(Flik.isSameNumber(128, 128));
    }

}
