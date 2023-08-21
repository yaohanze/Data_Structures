public class LinkedListDeque<Item> implements Deque<Item> {
    private class StuffNode {
        private Item item;
        private StuffNode next;
        private StuffNode prev;

        StuffNode() {
            item = null;
            next = null;
            prev = null;
        }

        StuffNode(Item i, StuffNode n) {
            item = i;
            next = n;
            prev = null;
        }
    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new StuffNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
        StuffNode p = other.sentinel.next;
        while (p != other.sentinel) {
            StuffNode n = new StuffNode(p.item, sentinel);
            n.prev = sentinel.prev;
            sentinel.prev.next = n;
            sentinel.prev = n;
            size += 1;
            p = p.next;
        }
    }

    @Override
    public void addFirst(Item item) {
        StuffNode n = new StuffNode(item, sentinel.next);
        n.prev = sentinel;
        sentinel.next.prev = n;
        sentinel.next = n;
        size += 1;
    }

    @Override
    public void addLast(Item item) {
        StuffNode n = new StuffNode(item, sentinel);
        n.prev = sentinel.prev;
        sentinel.prev.next = n;
        sentinel.prev = n;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StuffNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println(" ");
    }

    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        Item first = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first;
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        Item last = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last;
    }

    @Override
    public Item get(int index) {
        if (index < 0 || index > (size - 1)) {
            return null;
        } else {
            StuffNode p = sentinel.next;
            while (index > 0) {
                p = p.next;
                index -= 1;
            }
            return p.item;
        }
    }

    public Item getRecursive(int index) {
        StuffNode p = sentinel.next;
        if (index < 0 || index > (size - 1)) {
            return null;
        } else {
            return getRecursivehelper(index, sentinel.next);
        }
    }

    private Item getRecursivehelper(int index, StuffNode n) {
        if (index == 0) {
            return n.item;
        }
        return getRecursivehelper(index - 1, n.next);
    }
}
