import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> q = new Queue<>();
        q.enqueue("Zetalot");
        q.enqueue("Henry");
        q.enqueue("Abe");
        q.enqueue("Becker");
        q.enqueue("Joe");
        q.enqueue("Omar");
        q.enqueue("Itai");
        Queue<String> ms = QuickSort.quickSort(q);
        assertTrue(isSorted(ms));
    }

    @Test
    public void testMergeSort() {
        Queue<String> q = new Queue<>();
        q.enqueue("Joe");
        q.enqueue("Omar");
        q.enqueue("Itai");
        q.enqueue("Jack");
        q.enqueue("Zetalot");
        q.enqueue("Henry");
        q.enqueue("Abe");
        Queue<String> ms = MergeSort.mergeSort(q);
        assertTrue(isSorted(ms));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
