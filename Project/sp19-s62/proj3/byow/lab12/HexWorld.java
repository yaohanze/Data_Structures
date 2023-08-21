package byow.lab12;
//import org.junit.Test;
//import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 * @author Hanze Yao created at 4/18/2019
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);


    /**
     * Calculate the width of row r for a size s hexagon.
     *
     * @param s The size of the hexagon
     * @param r The row number where r = 0 is the bottom row
     * @return The width of row r for a size s hexagon
     */
    private static int hexRowWidth(int s, int r) {
        if (r >= 0 && r < s) {
            return s + 2 * r;
        } else {
            return s + 2 * (2 * s - r - 1);
        }
    }

    /**
     * Calculate the x coordinate of the leftmost tile in the rth
     * row of a hexagon, assuming the bottom row has an x_coordinate
     * of 0.
     *
     * @param s The size of the hexagon
     * @param r The row number where r = 0 is the bottom row
     * @return The x coordinate of the leftmost tile in the rth row of a hexagon
     */
    private static int hexRowOffset(int s, int r) {
        if (r >= 0 && r < s) {
            return -r;
        } else {
            return -2 * s + r + 1;
        }
    }

    /**
     * Add a row of the same tile.
     *
     * @param world The world to draw on
     * @param p     The leftmost position of the row
     * @param width The number of tiles wide to draw
     * @param t     The tile to draw
     */
    private static void addrow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; i++) {
            int x = p.getX() + i;
            int y = p.getY();
            world[x][y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Add a hexagon to the world.
     *
     * @param world The world to draw on
     * @param p     The bottom left coordinate of the hexagon
     * @param s     The size of the hexagon
     * @param t     The tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int i = 0; i < 2 * s; i++) {
            int rowY = p.getY() + i;
            int rowXstart = p.getX() + hexRowOffset(s, i);
            Position rowStartP = new Position(rowXstart, rowY);
            int rowWidth = hexRowWidth(s, i);
            addrow(world, rowStartP, rowWidth, t);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.TREE;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawRandomVerticalHexes(TETile[][] world, Position p, int s, int n) {
        if (n == 0) {
            return;
        }
        addHexagon(world, p, s, randomTile());
        int nextY = p.getY() - 2 * s;
        Position nextP = new Position(p.getX(), nextY);
        drawRandomVerticalHexes(world, nextP, s, n - 1);
    }

    private static Position topRightNeighbor(Position p, int s) {
        int trPx = p.getX() + 2 * s - 1;
        int trPy = p.getY() + s;
        Position trP = new Position(trPx, trPy);
        return trP;
    }

    private static Position bottomRoghtNeighbor(Position p, int s) {
        int brPx = p.getX() + 2 * s - 1;
        int brPy = p.getY() - s;
        Position brP = new Position(brPx, brPy);
        return brP;
    }

    //private static int calColumn(int a, int c, int i) {
        //if (i <= c / 2) {
            //return a + i;
        //} else {
            //return a + c / 2 - i + c / 2;
        //}
    //}

    //public void createHexagonWorld(TETile[][] world, int x, int y, int s, int a, int c) {
        //Position startP = new Position(x, y);
        //for (int i = 0; i <= c / 2; i ++) {
            //int column = calColumn(a, c, i);
            //drawRandomVerticalHexes(world, startP, s, column);
            //startP = topRightNeighbor(startP, s);
        //}
        //for (int j = c / 2 + 1; j < c; j++) {
            //int column = calColumn(a, c, j);
            //startP = bottomRoghtNeighbor(startP, s);
            //drawRandomVerticalHexes(world, startP, s, column);
        //}
    //}

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(32, 32);
        TETile[][] world = new TETile[32][32];
        for (int x = 0; x < 32; x += 1) {
            for (int y = 0; y < 32; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int startX = 2;
        int startY = 18;
        Position startP = new Position(startX, startY);
        drawRandomVerticalHexes(world, startP, 3, 3);
        startP = topRightNeighbor(startP, 3);
        drawRandomVerticalHexes(world, startP, 3, 4);
        startP = topRightNeighbor(startP, 3);
        drawRandomVerticalHexes(world, startP, 3, 5);
        startP = bottomRoghtNeighbor(startP, 3);
        drawRandomVerticalHexes(world, startP, 3, 4);
        startP = bottomRoghtNeighbor(startP, 3);
        drawRandomVerticalHexes(world, startP, 3, 3);

        ter.renderFrame(world);
    }
}
