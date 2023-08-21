package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {
    private static Position p;
    public static void generateAvatar(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        for (int i = width - 1; i >= 0; i--) {
            for (int j = height - 1; j >= 0; j--) {
                if (world[i][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.AVATAR;
                    Position ap = new Position(i, j);
                    p = ap;
                    return;
                }
            }
        }
    }

    public static Position getPosition() {
        return p;
    }

    public static void changePosition(int x, int y) {
        p = new Position(x, y);
    }
}
