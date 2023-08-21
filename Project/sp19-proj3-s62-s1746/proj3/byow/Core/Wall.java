package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Wall {
    private static int width = 80;
    private static int height = 30;

    public static void gennerateWall(TETile[][] world) {
        int indicator = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    indicator = 1;
                }
                if (indicator == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    indicator = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    indicator = 1;
                }
                if (indicator == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    indicator = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = width - 1; i >= 0; i--) {
            for (int j = height - 1; j >= 0; j--) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    indicator = 1;
                }
                if (indicator == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    indicator = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int j = height - 1; j >= 0; j--) {
            for (int i = width - 1; i >= 0; i--) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    indicator = 1;
                }
                if (indicator == 1 && world[i][j].equals(Tileset.NOTHING)) {
                    indicator = 0;
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = 0; i < width - 2; i++) {
            for (int j = 2; j < height; j++) {
                if (world[i][j].equals(Tileset.NOTHING)
                        && world[i + 1][j - 1].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = 2; i < width; i++) {
            for (int j = 2; j < height; j++) {
                if (world[i][j].equals(Tileset.NOTHING)
                        && world[i - 1][j - 1].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = 2; i < width; i++) {
            for (int j = 0; j < height - 2; j++) {
                if (world[i][j].equals(Tileset.NOTHING)
                        && world[i - 1][j + 1].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
        for (int i = 0; i < width - 2; i++) {
            for (int j = 0; j < height - 2; j++) {
                if (world[i][j].equals(Tileset.NOTHING)
                        && world[i + 1][j + 1].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }
}
