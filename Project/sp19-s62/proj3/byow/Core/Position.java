package byow.Core;

import java.util.Random;
import byow.Core.RandomUtils.*;
import byow.TileEngine.TETile;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void diagonalization(Position a, Position b) {
        if (a.getX() > b.getX()) {
            int temp = a.getX();
            a.x = b.getX();
            b.x = temp;
        }
        if (a.getY() > b.getY()) {
            int temp = a.getY();
            a.y = b.getY();
            b.y = temp;
        }
    }

    public boolean isInside(Room r) {
        Position ll = r.getLl();
        Position ur = r.getUr();
        int llx = ll.getX();
        int lly = ll.getY();
        int urx = ur.getX();
        int ury = ur.getY();
        int tx = getX();
        int ty = getY();
        return (tx >= llx) && (tx <= urx) && (ty >= lly) && (ty <= ury);
    }

    public static Position randomPosition(Random random, TETile[][] w) {
        int width = w[0].length;
        int height = w.length;
        int x = RandomUtils.uniform(random, 1, width);
        int y = RandomUtils.uniform(random, 1, height);
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Position ot = (Position) o;
        return (this.getX() == ot.getX()) && (this.getY() == ot.getY());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
