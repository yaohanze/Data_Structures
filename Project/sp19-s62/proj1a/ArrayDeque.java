public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        nextFirst = 1;
        nextLast = 2;
        size = 0;
    }

    //public ArrayDeque(ArrayDeque other) {
        //int len = other.items.length;
        //items = (T []) new Object[len];
        //size = other.size;
        //nextFirst = other.nextFirst;
        //nextLast = other.nextLast;
        //int start = Math.floorMod(nextFirst + 1, items.length);
        //for (int i = start; i != nextLast; i = Math.floorMod(i + 1, items.length)) {
            //items[i] = (T) other.items[i];
        //}
    //}

    public ArrayDeque(ArrayDeque other) {
        this();
        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
    }

    /**I use the floorMod in Math. Hint from Stackoverflow.*/
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = moveindex(nextFirst - 1); //Math.floorMod(nextFirst - 1, items.length);
        size += 1;
    }

    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = moveindex(nextLast + 1); //Math.floorMod(nextLast + 1, items.length);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int start = moveindex(nextFirst + 1); //Math.floorMod(nextFirst + 1, items.length);
        for (int i = start; i != nextLast; i = moveindex(i + 1)) {
            System.out.print(items[moveindex(i + 1)] + " ");
        }
        System.out.println(" ");
    }

    public T removeFirst() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        if (resizeornot()) {
            resize(items.length / 2);
        }
        int i = moveindex(nextFirst + 1); //Math.floorMod(nextFirst + 1, items.length);
        T first = items[i];
        items[i] = null;
        nextFirst = i;
        size -= 1;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        if (resizeornot()) {
            resize(items.length / 2);
        }
        int i = moveindex(nextLast - 1); //Math.floorMod(nextLast - 1, items.length);
        T last = items[i];
        items[i] = null;
        nextLast = i;
        size -= 1;
        return last;
    }

    public T get(int index) {
        if (index < 0 || index > (size - 1)) {
            return null;
        }
        int i = moveindex(nextFirst + index + 1);
        return items[i];
    }

    /**In this resize() method, I want to implement: (1) Expand the array
     * to double the length (2) Shrink the array to half the length. In this process I also
     * need to re-arrange the array. In this re-arrange process, I don't care about the
     * position of the item in the new array.*/
    private void resize(int cap) {
        T[] original = items;
        int i = nextFirst;
        int j = nextLast;
        items = (T []) new Object[cap];
        nextFirst = 1;
        nextLast = 2;
        int start = Math.floorMod(i + 1, original.length);
        for (int m = start; m != j; m = Math.floorMod(m + 1, original.length)) {
            items[nextLast] = original[m];
            nextLast = moveindex(nextLast + 1); //Math.floorMod(nextLast + 1, items.length);
        }
    }
    private int moveindex(int i) {
        return Math.floorMod(i, items.length);
    }

    private boolean resizeornot() {
        return size - 1 < items.length / 4 && items.length > 8;
    }
}
