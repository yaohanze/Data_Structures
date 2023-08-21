public class ArrayDeque<Item> implements Deque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (Item []) new Object[8];
        nextFirst = 1;
        nextLast = 2;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        int len = other.items.length;
        items = (Item []) new Object[len];
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        int start = Math.floorMod(nextFirst + 1, items.length);
        for (int i = start; i != nextLast; i = Math.floorMod(i + 1, items.length)) {
            items[i] = (Item) other.items[i];
        }
    }

    /**I use the floorMod in Math. Hint from Stackoverflow.*/
    @Override
    public void addFirst(Item item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size += 1;
    }

    @Override
    public void addLast(Item item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int start = Math.floorMod(nextFirst + 1, items.length);
        for (int i = start; i != nextLast; i = Math.floorMod(i + 1, items.length)) {
            System.out.print(items[Math.floorMod(i, items.length)]);
            System.out.print(" ");
        }
        System.out.println(" ");
    }

    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        if (size - 1 < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
        int i = Math.floorMod(nextFirst + 1, items.length);
        Item first = items[i];
        items[i] = null;
        nextFirst = i;
        size -= 1;
        return first;
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        if (size - 1 < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
        int i = Math.floorMod(nextLast - 1, items.length);
        Item last = items[i];
        items[i] = null;
        nextLast = i;
        size -= 1;
        return last;
    }

    @Override
    public Item get(int index) {
        int i = Math.floorMod(nextFirst + index + 1, items.length);
        return items[i];
    }

    /**In this resize() method, I want to implement: (1) Expand the array
     * to double the length (2) Shrink the array to half the length. In this process I also
     * need to re-arrange the array. In this re-arrange process, I don't care about the
     * position of the item in the new array.*/
    private void resize(int cap) {
        Item[] original = items;
        int i = nextFirst;
        int j = nextLast;
        items = (Item []) new Object[cap];
        nextFirst = 1;
        nextLast = 2;
        int start = Math.floorMod(i + 1, original.length);
        for (int m = start; m != j; m = Math.floorMod(m + 1, original.length)) {
            items[nextLast] = original[m];
            nextLast = Math.floorMod(nextLast + 1, items.length);
        }
    }
}
