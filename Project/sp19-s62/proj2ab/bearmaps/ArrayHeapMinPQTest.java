package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ArrayHeapMinPQTest {
    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        assertFalse(ahm.contains(1));
        ahm.add(1, 1.2);
        ahm.add(2, 1.3);
        ahm.add(3, 1.4);
        ahm.add(4, 2.0);
        ahm.add(5, 3.1);
        ahm.add(6, 3.2);
        ahm.add(7, 1.1);
        assertTrue(ahm.contains(2));
        assertTrue(ahm.contains(4));
        assertFalse(ahm.contains(8));
        assertFalse(ahm.contains(10));
    }

    @Test
    public void testGetsmallest() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(1, 1.2);
        ahm.add(2, 1.3);
        ahm.add(3, 1.4);
        ahm.add(4, 2.0);
        ahm.add(5, 3.1);
        ahm.add(6, 3.2);
        ahm.add(7, 1.1);
        int actual = ahm.getSmallest();
        assertEquals(7, actual);
        ArrayHeapMinPQ<Integer> ahm1 = new ArrayHeapMinPQ<>();
        ahm1.add(1, 5.0);
        ahm1.add(2, 4.0);
        ahm1.add(3, 3.0);
        ahm1.add(4, 2.0);
        ahm1.add(5, 1.0);
        int actual1 = ahm1.getSmallest();
        assertEquals(5, actual1);
    }

    @Test
    public void testRemovesmallest() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(1, 5.0);
        ahm.add(2, 4.0);
        ahm.add(3, 3.0);
        ahm.add(4, 2.0);
        ahm.add(5, 1.0);
        int actual = ahm.removeSmallest();
        assertEquals(5, actual);
        int newsmallest = ahm.getSmallest();
        assertEquals(4, newsmallest);
        int size = ahm.size();
        assertEquals(4, size);
        assertFalse(ahm.contains(5));
    }

    @Test
    public void testSize() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(1, 5.0);
        ahm.add(2, 4.0);
        ahm.add(3, 3.0);
        ahm.add(4, 2.0);
        ahm.add(5, 1.0);
        int actual = ahm.size();
        assertEquals(5, actual);
        ahm.removeSmallest();
        int actual1 = ahm.size();
        assertEquals(4, actual1);
        ahm.add(6, 1.1);
        int actual2 = ahm.size();
        assertEquals(5, actual2);
        for (int i = 0; i < 5; i++) {
            ahm.removeSmallest();
        }
        int actual3 = ahm.size();
        assertEquals(0, actual3);
    }

    @Test
    public void testChangepriority() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(1, 1.2);
        ahm.add(2, 1.3);
        ahm.add(3, 1.4);
        ahm.add(4, 2.0);
        ahm.add(5, 3.1);
        ahm.add(6, 3.2);
        ahm.add(7, 1.1);
        ahm.changePriority(7, 3.0);
        int actual = ahm.getSmallest();
        assertEquals(1, actual);
    }
}
