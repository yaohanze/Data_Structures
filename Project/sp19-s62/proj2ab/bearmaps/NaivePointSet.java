package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point nearestPoint = points.get(0);
        Point target = new Point(x, y);
        double nearestDistance = Point.distance(target, nearestPoint);
        for (Point p : points) {
            double distance = Point.distance(target, p);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }
}
