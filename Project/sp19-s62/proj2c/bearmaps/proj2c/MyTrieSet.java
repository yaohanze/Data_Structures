package bearmaps.proj2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    private static class Node {
        private char ch;
        private boolean isKey;
        private HashMap<Character, Node> map;
        private Node(char c, boolean b) {
            ch = c;
            isKey = b;
            map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node('s', false);
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node('s', false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        if (curr.isKey) {
            return true;
        }
        return false;
    }



    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> results = new ArrayList<>();
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        collect(curr, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, ArrayList<String> results) {
        if (x == null) {
            return;
        }
        if (x.isKey) {
            results.add(prefix.toString());
        }
        for (char c: x.map.keySet()) {
            prefix.append(c);
            collect(x.map.get(c), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
