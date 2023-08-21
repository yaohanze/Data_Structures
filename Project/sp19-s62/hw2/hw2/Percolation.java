/**
 * CS61b Spring 2019 HW2
 * Created by Hanze Yao @ 3/3/2019
 */
package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int numopen;
    private WeightedQuickUnionUF site;
    private WeightedQuickUnionUF site1;
    private int size;

    /**
     * Create N-By-N grid with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        numopen = 0;
        site = new WeightedQuickUnionUF(N * N + 2);
        site1 = new WeightedQuickUnionUF(N * N + 2);
        size = N;
    }

    /**
     * Open this site if it is not open.
     */
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        numopen += 1;
        if (size == 1) {
            site.union(pos(row, col), 0);
            site1.union(pos(row, col), 0);
            site.union(pos(row, col), size * size + 1);
            site1.union(pos(row, col), size * size + 1);
        } else if (size == 2) {
            if (row == 0) {
                site.union(pos(row, col), 0);
                site1.union(pos(row, col), 0);
                connectBottom(row, col);
            }
            if (row == size - 1) {
                site.union(pos(row, col), size * size + 1);
                site1.union(pos(row, col), size * size + 1);
                connectTop(row, col);
            }
        } else {
            connect(row, col);
        }
    }

    /**
     * Is the site open?
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    /**
     * Is the site full?
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        return site.connected(0, pos(row, col));
    }

    /**
     * Return number of open sites.
     */
    public int numberOfOpenSites() {
        return numopen;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return site1.connected(0, size * size + 1);
    }

    /**
     * Helper function that validates the row and column value.
     */
    private void validate(int row, int col) {
        if ((row < 0 || row >= size) || (col < 0 || col >= size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     *  Helper funtion that returns the corresponding position in the WQUUF.
     */
    private int pos(int row, int col) {
        int position = row * size + col + 1;
        return position;
    }

    /**
     *  Helper function that determines whether connects the site with the sites around it.
     */
    private void connect(int row, int col) {
        if (row == 0) {
            connectBottom(row, col);
            connectVirtualTop(row, col);
        } else if (row > 0 && row < size - 1 && col == 0) {
            connectBottom(row, col);
            connectRight(row, col);
            connectTop(row, col);
        } else if (row > 0 && row < size - 1 && col == size - 1) {
            connectBottom(row, col);
            connectLeft(row, col);
            connectTop(row, col);
        } else if (row == size - 1 && col == 0) {
            connectTop(row, col);
            connectRight(row, col);
            connectVirtualBottom(row, col);
        } else if (row == size - 1 && col > 0 && col < size - 1) {
            connectLeft(row, col);
            connectTop(row, col);
            connectRight(row, col);
            connectVirtualBottom(row, col);
        } else if (row == size - 1 && col == size - 1) {
            connectLeft(row, col);
            connectTop(row, col);
            connectVirtualBottom(row, col);
        } else {
            connectLeft(row, col);
            connectRight(row, col);
            connectTop(row, col);
            connectBottom(row, col);
        }
    }

    /**
     * Helper function determines whether connects with the site on the left.
     */
    private void connectLeft(int row, int col) {
        if (isOpen(row, col - 1)) {
            site.union(pos(row, col - 1), pos(row, col));
            site1.union(pos(row, col - 1), pos(row, col));
        }
    }

    /**
     * Helper function determines whether connects with the site on the right.
     */
    private void connectRight(int row, int col) {
        if (isOpen(row, col + 1)) {
            site.union(pos(row, col + 1), pos(row, col));
            site1.union(pos(row, col + 1), pos(row, col));
        }
    }

    /**
     * Helper function determines whether connects with the site on the top.
     */
    private void connectTop(int row, int col) {
        if (isOpen(row - 1, col)) {
            site.union(pos(row - 1, col), pos(row, col));
            site1.union(pos(row - 1, col), pos(row, col));
        }
    }

    /**
     * Helper function determines whether connects with the site on the bottom.
     */
    private void connectBottom(int row, int col) {
        if (isOpen(row + 1, col)) {
            site.union(pos(row + 1, col), pos(row, col));
            site1.union(pos(row + 1, col), pos(row, col));
        }
    }

    /**
     * Helper function determines whether connects with the virtual bottom site.
     */
    private void connectVirtualBottom(int row, int col) {
        if (isFull(row, col)) {
            site.union(size * size + 1, pos(row, col));
        }
        site1.union(size * size + 1, pos(row, col));
    }

    /**
     * Helper function determines whether connects with the virtual top site.
     */
    private void connectVirtualTop(int row, int col) {
        site.union(pos(row, col), 0);
        site1.union(pos(row, col), 0);
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 3);
        System.out.println(p.isOpen(1, 2));
        System.out.println(p.isOpen(1, 3));
        System.out.println(p.isFull(1, 3));
        p.open(1, 2);
        System.out.println(p.isFull(1, 2));
        p.open(0, 2);
        System.out.println(p.isFull(0, 2));
        System.out.println(p.isFull(1, 3));
        p.open(3, 2);
        System.out.println(p.isFull(3, 2));
        p.open(2, 2);
        System.out.println(p.percolates());
        System.out.println(p.isFull(2, 2));
        p.open(3, 0);
        p.open(2, 0);
        System.out.println(p.isFull(3, 0));
        System.out.println(p.isFull(2, 0));
        System.out.println(p.numberOfOpenSites());
    }
}
