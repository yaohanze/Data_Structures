package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        int i = arb.dequeue();
        assertEquals(1, i);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);
        arb.enqueue(11);
        assertTrue(arb.isFull());
        int j = arb.peek();
        assertTrue(arb.isFull());
        assertEquals(2, j);
        int k = arb.dequeue();
        assertFalse(arb.isFull());
        assertEquals(2, k);
        ArrayRingBuffer<Integer> other = new ArrayRingBuffer<>(10);
        other.enqueue(3);
        other.enqueue(4);
        other.enqueue(5);
        other.enqueue(6);
        other.enqueue(7);
        other.enqueue(8);
        other.enqueue(9);
        other.enqueue(10);
        other.enqueue(11);
        assertTrue(arb.equals(other));
    }
}
