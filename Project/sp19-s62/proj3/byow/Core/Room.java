package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

public class Room {
    private TETile[][] world;
    private Position ll;
    private Position ur;

    public Room(TETile[][] world, Position a, Position b) {
        this.world = world;
        ll = a;
        ur = b;
    }

    //public boolean isOutofBound() {
        //int llx = ll.getX();
        //int lly = ll.getY();
        //int urx = ur.getX();
        //int ury = ur.getY();
        //int width = world[0].length;
        //int height = world.length;
        //if (!((llx > 0) && (llx < width - 1))) {
            //return false;
        //}
        //if (!((lly > 0) && (lly < height - 1))) {
            //return false;
        //}
        //if (!((urx > 0) && (urx < width - 1))) {
            //return false;
        //}
        //if (!((ury > 0) && (ury < height - 1))) {
            //return false;
        //}
        //return true;
    //}

    public boolean isOverlap(Room r) {
        Position lr = new Position(ur.getX(), ll.getY());
        Position ul = new Position(ll.getX(), ur.getY());
        Position rll = r.getLl();
        Position rur = r.getUr();
        Position rlr = new Position(rur.getX(), rll.getY());
        Position rul = new Position(rll.getX(), rur.getY());
        if (ll.isInside(r) || lr.isInside(r) || ul.isInside(r) || ur.isInside(r)) {
            return true;
        }
        if (rll.isInside(this) || rlr.isInside(this) || rul.isInside(this) || rur.isInside(this)) {
            return true;
        }
        return false;
    }

    public Room randomRoom(Random random, TETile[][] w) {
        Position a = Position.randomPosition(random, world);
        Position b = Position.randomPosition(random, world);
        while(b.equals(a)) {
            b = Position.randomPosition(random, world);
        }
        Position.diagonalization(a, b);
        return new Room(world, a, b);
    }

    public Position getLl() {
        return this.ll;
    }

    public Position getUr() {
        return this.ur;
    }

    public void draw() {
        for (int i = ll.getX(); i <= ur.getX(); i++) {
            for (int j = ll.getY(); j <= ur.getY(); j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
        for (int m = ll.getX() - 1; m <= ur.getX() + 1; m++) {
            world[m][ll.getY() - 1] = Tileset.WALL;
            world[m][ur.getY() + 1] = Tileset.WALL;
        }
        for (int n = ll.getY() + 1; n < ur.getY(); n++) {
            world[ll.getX() - 1][n] = Tileset.WALL;
            world[ur.getX() + 1][n] = Tileset.WALL;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Room ot = (Room) o;
        return (this.ll.equals(ot.ll)) && (this.ur.equals(ot.ur));
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
