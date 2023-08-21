package es.datastructur.synthesizer;

import java.util.HashSet;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // reate a buffer with capacity = SR / frequency. You'll need to
        // cast the result of this division operation into an int. For
        // better accuracy, use the Math.round() function before casting.
        // Your buffer should be initially filled with zeros.
        int capacity = (int) Math.round(SR / frequency);
        ArrayRingBuffer<Double> a = new ArrayRingBuffer<Double>(capacity);
        for (int i = 0; i < a.capacity(); i++) {
            a.enqueue(0.0);
        }
        buffer = a;
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // Dequeue everything in buffer, and replace with random numbers
        // between -0.5 and 0.5. You can get such a number by using:
        // double r = Math.random() - 0.5;
        //
        // Make sure that your random numbers are different from each
        // other.
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
        }
        HashSet<Double> randomnum = new HashSet<>(buffer.capacity());
        for (int j = 0; j < buffer.capacity(); j++) {
            double r = Math.random() - 0.5;
            while (randomnum.contains(r)) {
                r = Math.random() - 0.5;
            }
            randomnum.add(r);
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // Dequeue the front sample and enqueue a new sample that is
        // the average of the two multiplied by the DECAY factor.
        // Do not call StdAudio.play().
        double front = buffer.dequeue();
        double second = buffer.peek();
        double newDouble = DECAY * (front + second) / 2;
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // Return the correct thing.
        double fbuffer = buffer.peek();
        return fbuffer;
    }
}
