package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you",
            "byow/TileEngine/textures/avatar.png");
    public static final TETile WALL = new TETile(' ', new Color(216, 128, 128), Color.darkGray,
             "wall", "byow/TileEngine/textures/wall.png");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "byow/TileEngine/textures/floor.png");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing",
            "byow/TileEngine/textures/grass.png");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door", "byow/TileEngine/textures/door.png");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door", "byow/TileEngine/textures/door.png");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile AVATAR1 = new TETile('♠', Color.green, Color.black,
            "avatar on almost wall", "byow/TileEngine/textures/avatar1.png");
    public static final TETile AVATAR2 = new TETile('♠', Color.green, Color.black,
            "avatar on almost wall", "byow/TileEngine/textures/avatar1.png");
    public static final TETile WALL1 = new TETile('X', Color.green, Color.black, "almost wall",
            "byow/TileEngine/textures/wall1.png");
    public static final TETile BEAN = new TETile('.', Color.green, Color.black, "bean",
            "byow/TileEngine/textures/bean.png");
}


