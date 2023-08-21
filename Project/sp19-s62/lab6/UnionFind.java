public class UnionFind {

    private int[] setarray;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        setarray = new int[n];
        for (int i = 0; i < n; i++) {
            setarray[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= setarray.length) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        if (setarray[v1] < 0) {
            return (-1) * setarray[v1];
        }
        int root = find(v1);
        int size = (-1) * (parent(root));
        return size;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return setarray[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if (connected(v1, v2)) {
            return;
        }
        if (size1 > size2) {
            setarray[root2] = v1;
            setarray[root1] -= size2;
        } else {
            setarray[root1] = v2;
            setarray[root2] -= size1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (setarray[vertex] < 0) {
            return vertex;
        }
        return find(parent(vertex));
    }

    public static void main(String[] args) {
        UnionFind ds = new UnionFind(5);
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(3, 4);
        ds.union(0, 3);
    }

}
