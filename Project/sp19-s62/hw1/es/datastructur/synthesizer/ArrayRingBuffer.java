package es.datastructur.synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayDeque;

// Make sure to that this class and all of its methods are public
// Make sure to add the override tag for all overridden methods
// Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T>  implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class AIterator<T> implements Iterator<T> {
        ArrayRingBuffer<T> queue;
        ArrayDeque<T> array;

        AIterator(ArrayRingBuffer<T> queue) {
            this.queue = queue;
            this.array = new ArrayDeque<>();
            int qlength = queue.fillCount();
            for (int i = 0; i < qlength; i++) {
                array.addLast(queue.dequeue());
            }
            ArrayDeque<T> copy = array.clone();
            for (int j = 0; j < qlength; j++) {
                queue.enqueue(copy.removeLast());
            }
        }

        @Override
        public boolean hasNext() {
            return array.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("End of queue");
            }
            return array.removeFirst();
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // Create new array with capacity elements.
        // first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Return capacity of the ring buffer.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Return the number of items currently in the buffer.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = move(last, 1);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T fitem = rb[first];
        rb[first] = null;
        first = move(first, 1);
        fillCount -= 1;
        return fitem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T fitem = rb[first];
        return fitem;
    }

    // When you get to part 4, implement the needed code to support
    // iteration and equals.
    public Iterator<T> iterator() {
        return new AIterator<T>(this);
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> ar = (ArrayRingBuffer<T>) o;
        if (fillCount() != ar.fillCount()) {
            return false;
        }
        Iterator<T> org = this.iterator();
        Iterator<T> oth = ar.iterator();
        while (org.hasNext()) {
            if (!org.next().equals(oth.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function for move index.
     */
    private int move(int x, int index) {
        return Math.floorMod(x + index, capacity());
    }
}
