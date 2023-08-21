import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private ArrayList<Entry> bins;
    private int size;
    private int initialSize;
    private double loadFactor;
    private HashSet<K> keySet;

    public MyHashMap(int initialSize, double loadFactor) {
        if (initialSize < 1 || loadFactor <= 0) {
            throw new IllegalArgumentException();
        }
        bins = new ArrayList<Entry>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            bins.add(i, null);
        }
        size = 0;
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.keySet = new HashSet<>();
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        bins = new ArrayList<>(initialSize);
        size = 0;
        keySet = new HashSet<>();
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (keySet == null) {
            return null;
        }
        if (!keySet.contains(key)) {
            return null;
        }
        int num = bucketnum(key);
        Entry bucket = bins.get(num);
        if (bucket == null) {
            return null;
        }
        Entry lookup = bucket.get(key);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (keySet.contains(key)) {
            int num = bucketnum(key);
            Entry bucket = bins.get(num);
            Entry lookup = bucket.get(key);
            lookup.val = value;
        } else {
            int newSize = size() + 1;
            if (newSize > initialSize * loadFactor) {
                resize(initialSize * 2);
            }
            int num = bucketnum(key);
            Entry bucket = bins.get(num);
            bucket = new Entry(key, value, bucket);
            bins.set(num, bucket);
            size = newSize;
            keySet.add(key);
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private class Entry {
        /**
         * Stores KEY as the key in this key-value pair, VAL as the value, and
         * NEXT as the next node in the linked list.
         */
        Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /**
         * Returns the Entry in this linked list of key-value pairs whose key
         * is equal to KEY, or null if no such Entry exists.
         */
        Entry get(K k) {
            if (k != null && k.equals(key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(k);
        }

        /** Stores the key of the key-value pair of this node in the list. */
        K key;
        /** Stores the value of the key-value pair of this node in the list. */
        V val;
        /** Stores the next Entry in the linked list. */
        Entry next;
    }

    private int bucketnum(K key) {
        return (key.hashCode() & 0x7fffffff) % initialSize;
    }

    private void resize(int s) {
        MyHashMap<K, V> temp = new MyHashMap<>(s, loadFactor);
        for (int i = 0; i < initialSize; i++) {
            Entry p = bins.get(i);
            while (p != null) {
                temp.put(p.key, p.val);
                p = p.next;
            }
        }
        bins = temp.bins;
        initialSize = s;
    }
}
