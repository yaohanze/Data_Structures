package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Door {
    public static void generateDoor(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (!world[i][j].equals(Tileset.WALL)) {
                    continue;
                }
                if (!((world[i - 1][j].equals(Tileset.WALL)
                        && world[i + 1][j].equals(Tileset.WALL))
                        || (world[i][j + 1].equals(Tileset.WALL)
                        && world[i][j - 1].equals(Tileset.WALL)))) {
                    continue;
                }
                if ((world[i - 1][j].equals(Tileset.NOTHING)
                        && world[i + 1][j].equals(Tileset.FLOOR))
                        || ((world[i + 1][j].equals(Tileset.NOTHING)
                        && world[i - 1][j].equals(Tileset.FLOOR)))) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    return;
                }
                if ((world[i][j - 1].equals(Tileset.NOTHING)
                        && world[i][j + 1].equals(Tileset.FLOOR))
                        || ((world[i][j + 1].equals(Tileset.NOTHING)
                        && world[i][j - 1].equals(Tileset.FLOOR)))) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    return;
                }
            }
        }
    }
}
