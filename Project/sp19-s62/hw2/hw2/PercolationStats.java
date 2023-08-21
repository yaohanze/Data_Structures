/**
 * CS61b Spring 2019 HW2
 * Created by Hanze Yao @ 3/3/2019
 */
package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] thresold;
    private int testnum;

    /**
     * Perform T independent experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        thresold = new double[T];
        testnum = T;
        for (int i = 0; i < T; i++) {
            int num = 0;
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(N);
                    col = StdRandom.uniform(N);
                }
                p.open(row, col);
                num += 1;
            }
            thresold[i] = (double) num / (N * N);
        }
    }

    /**
     * Sample mean of percolation thresold.
     */
    public double mean() {
        return StdStats.mean(thresold);
    }

    /**
     * Sample standard deviation of percolation thresold.
     */
    public double stddev() {
        if (testnum == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresold);
    }

    /**
     * Low endpoint of 95% confidence interval.
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt((double) testnum);
    }

    /**
     * High endpoint of 95% confidence interval.
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt((double) testnum);
    }
}
