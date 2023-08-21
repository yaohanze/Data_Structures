package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private HashMap itemMap;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        itemMap = new HashMap();
        items.add(null);
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode node = new PriorityNode(item, priority);
        items.add(node);
        int index = size();
        itemMap.put(item, index);
        swim(node, index);
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        checkEmpty();
        T smallest = items.get(1).getItem();
        return smallest;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        checkEmpty();
        T smallestItem = getSmallest();
        if (size() == 1) {
            items.remove(size());
            itemMap.remove(smallestItem);
            return smallestItem;
        }
        PriorityNode last = items.remove(size());
        items.set(1, last);
        itemMap.remove(smallestItem);
        itemMap.replace(last.getItem(), 1);
        sink(last, 1);
        return smallestItem;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return items.size() - 1;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = (int) itemMap.get(item);
        PriorityNode node = items.get(index);
        double op = node.getPriority();
        node.setPriority(priority);
        if (priority > op) {
            sink(node, index);
        } else {
            swim(node, index);
        }
    }

    private void swim(PriorityNode node, int index) {
        if (index == 1) {
            return;
        }
        int parentIndex = index / 2;
        PriorityNode parent = items.get(parentIndex);
        double p = node.getPriority();
        double pp = parent.getPriority();
        if (p < pp) {
            items.set(parentIndex, node);
            items.set(index, parent);
            itemMap.replace(node.getItem(), parentIndex);
            itemMap.replace(parent.getItem(), index);
            swim(node, parentIndex);
        }
        return;
    }

    private void checkEmpty() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
    }

    private void sink(PriorityNode node, int index) {
        int leftChildIndex = index * 2;
        int rightChildIndex = index * 2 + 1;
        int size = size();
        if (leftChildIndex > size && rightChildIndex > size) {
            return;
        } else if (leftChildIndex <= size && rightChildIndex > size) {
            PriorityNode leftChild = items.get(leftChildIndex);
            if (node.getPriority() <= leftChild.getPriority()) {
                return;
            }
            items.set(index, leftChild);
            items.set(leftChildIndex, node);
            itemMap.replace(leftChild.getItem(), index);
            itemMap.replace(node.getItem(), leftChildIndex);
            sink(node, leftChildIndex);
        } else {
            PriorityNode leftChild = items.get(leftChildIndex);
            PriorityNode rightChild = items.get(rightChildIndex);
            double p = node.getPriority();
            double lp = leftChild.getPriority();
            double rp = rightChild.getPriority();
            if (p <= lp && p <= rp) {
                return;
            } else if (p > lp && p <= rp) {
                items.set(index, leftChild);
                items.set(leftChildIndex, node);
                itemMap.replace(leftChild.getItem(), index);
                itemMap.replace(node.getItem(), leftChildIndex);
                sink(node, leftChildIndex);
            } else if (p <= lp && p > rp) {
                items.set(index, rightChild);
                items.set(rightChildIndex, node);
                itemMap.replace(rightChild.getItem(), index);
                itemMap.replace(node.getItem(), rightChildIndex);
                sink(node, rightChildIndex);
            } else {
                if (lp <= rp) {
                    items.set(index, leftChild);
                    items.set(leftChildIndex, node);
                    itemMap.replace(leftChild.getItem(), index);
                    itemMap.replace(node.getItem(), leftChildIndex);
                    sink(node, leftChildIndex);
                } else {
                    items.set(index, rightChild);
                    items.set(rightChildIndex, node);
                    itemMap.replace(rightChild.getItem(), index);
                    itemMap.replace(node.getItem(), rightChildIndex);
                    sink(node, rightChildIndex);
                }
            }
        }
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
