package hw3.hash;

import java.util.LinkedList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         *Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        LinkedList<Oomage>[] ht = new LinkedList[M];
        int N = oomages.size();
        for (int i = 0; i < M; i++) {
            ht[i] = new LinkedList<Oomage>();
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            ht[bucketNum].addLast(o);
        }
        for (int j = 0; j < M; j++) {
            if (ht[j].size() < N / 50 || ht[j].size() > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
