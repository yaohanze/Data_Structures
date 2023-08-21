package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class KDTreeTest {
    @Test
    public void testNearest() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            List<Point> points = new ArrayList<>();
            HashSet<Point> existingpoints = new HashSet<>();
            for (int j = 0; j < 10000; j++) {
                Point p = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                while (existingpoints.contains(p)) {
                    p = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                }
                existingpoints.add(p);
                points.add(p);
            }
            KDTree kdt = new KDTree(points);
            NaivePointSet nps = new NaivePointSet(points);
            double x = rand.nextDouble() * 1000;
            double y = rand.nextDouble() * 1000;
            Point expected = nps.nearest(x, y);
            Point actual = kdt.nearest(x, y);
            System.out.println("target: " + x + " " + y);
            System.out.println("expected: " + expected.getX() + " " + expected.getY());
            System.out.println("actual: " + actual.getX() + " " +  actual.getY());
            assertEquals(expected, actual);
        }
    }
}
