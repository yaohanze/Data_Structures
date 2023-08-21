public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode prev;

        public StuffNode() {
            item = null;
            next = null;
            prev = null;
        }

        public StuffNode(T i, StuffNode n) {
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

    //public LinkedListDeque(LinkedListDeque other) {
        //sentinel = new StuffNode();
        //sentinel.next = sentinel;
        //sentinel.prev = sentinel;
        //size = 0;
        //StuffNode p = other.sentinel.next;
        //while (p != other.sentinel) {
            //StuffNode n = new StuffNode(p.item, sentinel);
            //n.prev = sentinel.prev;
            //sentinel.prev.next = n;
            //sentinel.prev = n;
            //size += 1;
            //p = p.next;
        //}
    //}

    public LinkedListDeque(LinkedListDeque other) {
        this();
        for(int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
    }

    public void addFirst(T item) {
        StuffNode n = new StuffNode(item, sentinel.next);
        n.prev = sentinel;
        sentinel.next.prev = n;
        sentinel.next = n;
        size += 1;
    }

    public void addLast(T item) {
        StuffNode n = new StuffNode(item, sentinel);
        n.prev = sentinel.prev;
        sentinel.prev.next = n;
        sentinel.prev = n;
        size += 1;
    }

   /*I got this simplified version of isEmpty() from IntelliJ suggestion.*/
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println(" ");
    }

    public T removeFirst() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            System.out.println("Array is empty. Can't remove anything!");
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last;
    }

    public T get(int index) {
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

    public T getRecursive(int index) {
        StuffNode p = sentinel.next;
        if (index < 0 || index > (size - 1)) {
            return null;
        } else {
            return getRecursivehelper(index, sentinel.next);
        }
    }

    private T getRecursivehelper(int index, StuffNode n) {
        if (index == 0) {
            return n.item;
        }
        return getRecursivehelper(index - 1, n.next);
    }
}
