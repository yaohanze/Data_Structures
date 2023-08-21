package bearmaps;

import java.util.Collections;
import java.util.List;

public class KDTree {

    private Node root;

    public KDTree(List<Point> points) {
        Collections.shuffle(points);
        Point first = points.get(0);
        this.root = new Node(first);
        for (int i = 1; i < points.size(); i++) {
            Node n = new Node(points.get(i));
            insert(root, n);
        }
    }

    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        return nearest(root, target, root).p;
    }

    private Node nearest(Node n, Point target, Node best) {
        if (n == null) {
            return best;
        }
        double bestDist = Point.distance(target, best.p);
        double newDist = Point.distance(target, n.p);
        if (newDist < bestDist) {
            best = n;
        }
        Node goodside = null;
        Node badside = null;
        if (n.depth % 2 == 0) {
            if (target.getX() < n.p.getX()) {
                goodside = n.left;
                badside = n.right;
            } else {
                goodside = n.right;
                badside = n.left;
            }
        } else {
            if (target.getY() < n.p.getY()) {
                goodside = n.left;
                badside = n.right;
            } else {
                goodside = n.right;
                badside = n.left;
            }
        }
        best = nearest(goodside, target, best);
        if (n.depth % 2 == 0) {
            if (Math.abs(target.getX() - n.p.getX()) < bestDist) {
                best = nearest(badside, target, best);
            }
        } else {
            if (Math.abs(target.getY() - n.p.getY()) < bestDist) {
                best = nearest(badside, target, best);
            }
        }
        return best;
    }

    private void insert(Node current, Node newNode) {
        if (current.depth % 2 == 0) {
            double currentX = current.p.getX();
            double newX = newNode.p.getX();
            if (newX < currentX) {
                if (current.left == null) {
                    current.left = newNode;
                    newNode.depth = current.depth + 1;
                    return;
                }
                insert(current.left, newNode);
            } else {
                if (current.right == null) {
                    current.right = newNode;
                    newNode.depth = current.depth + 1;
                    return;
                }
                insert(current.right, newNode);
            }
        } else {
            double currentY = current.p.getY();
            double newY = newNode.p.getY();
            if (newY < currentY) {
                if (current.left == null) {
                    current.left = newNode;
                    newNode.depth = current.depth + 1;
                    return;
                }
                insert(current.left, newNode);
            } else {
                if (current.right == null) {
                    current.right = newNode;
                    newNode.depth = current.depth + 1;
                    return;
                }
                insert(current.right, newNode);
            }
        }
    }

    private class Node {
        private Point p;
        private Node left;
        private Node right;
        private int depth;

        Node(Point p) {
            this.p = p;
            left = null;
            right = null;
            this.depth = 0;
        }
    }
}
