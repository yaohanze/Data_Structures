package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private Position avatarPosition;
    private String ip;
    private int point;
    private int target;
    private int level;
    private long sd;
    private Random rand;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        Mainmenu mm = new Mainmenu(40, 40);
        point = 0;
        level = 1;
        target = (level + 8) * 10 - 20;
        mm.drawMenu();
        StringBuilder str = new StringBuilder();
        while (str.length() < 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                str.append(input);
            }
        }
        if (str.toString().equals("n")) {
            String input = mm.newGame(str);
            mm.startGame();
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] world = interactWithInputString(input);
            int num = level + 8;
            generateRandomBean(world, num);
            ter.setPoint(point);
            ter.setLevel(level);
            ter.setTarget(target);
            ter.renderFrame(world);
            useKeyboard(str, world, mm);
        } else if (str.toString().equals("l")) {
            String m = mm.loadGame();
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] world = interactWithInputString(m);
            ter.setPoint(point);
            ter.setLevel(level);
            ter.setTarget(target);
            ter.renderFrame(world);
            useKeyboard(str, world, mm);
        } else if (str.toString().equals("r")) {
            String rpl = mm.loadGame();
            StringBuilder s = new StringBuilder();
            int i = 0;
            while (i < rpl.length()) {
                char c = rpl.charAt(i);
                s.append(c);
                if (c == 's') {
                    break;
                }
                i += 1;
            }
            String initialWorld = s.toString();
            TETile[][] world = interactWithInputString(initialWorld);
            ter.initialize(WIDTH, HEIGHT);
            ter.setPoint(point);
            ter.setLevel(level);
            ter.setTarget(target);
            ter.renderFrame(world);
            if (rpl.length() > i + 1) {
                int j = i + 1;
                while (j < rpl.length()) {
                    char mo = rpl.charAt(j);
                    moveAvatar(mo, world);
                    StdDraw.pause(50);
                    ter.renderFrame(world);
                    j += 1;
                }
            }
            useKeyboard(str, world, mm);
        } else if (str.toString().equals("a")) {
            String avatarName = mm.nameAvatar(str);
            ter.setAvatarName(avatarName);
            interactWithKeyboard();
        } else if (str.toString().equals("h")) {
            mm.help();
            interactWithKeyboard();
        } else if (str.toString().equals("q")) {
            System.exit(0);
        }
    }

    private void useKeyboard(StringBuilder str, TETile[][] world, Mainmenu mm) {
        while (true) {
            ter.setPoint(point);
            ter.setLevel(level);
            ter.setTarget(target);
            ter.renderFrame(world);
            if (StdDraw.hasNextKeyTyped()) {
                char i = StdDraw.nextKeyTyped();
                if (i == ':') {
                    break;
                }
                str.append(i);
                moveAvatar(i, world, mm);
                ter.setPoint(point);
                ter.setLevel(level);
                ter.setTarget(target);
                ter.renderFrame(world);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char j = StdDraw.nextKeyTyped();
                sb.append(j);
                if (j == 'q') {
                    String prev = mm.getInput();
                    String curr = str.toString();
                    String total = prev + curr;
                    mm.endGameSave(total);
                }
            }
        }
    }

    private void generateRandomBean(TETile[][] world, int num) {
        for (int i = 0; i < num; i++) {
            Position p = Position.randomPosition(rand, world);
            while (!world[p.getX()][p.getY()].equals(Tileset.FLOOR)) {
                p = Position.randomPosition(rand, world);
            }
            world[p.getX()][p.getY()] = Tileset.BEAN;
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        StringBuilder str = new StringBuilder();
        if (input.charAt(0) == 'n') {
            str.append(input.charAt(0));
            int i = 1;
            long seed = 0;
            while (input.charAt(i) != 's') {
                str.append(input.charAt(i));
                seed *= 10;
                seed += input.charAt(i);
                i += 1;
            }
            str.append(input.charAt(i));
            Random random = new Random(seed);
            sd = seed;
            rand = random;
            RandomWorldGenerator rwg = new RandomWorldGenerator(finalWorldFrame, random);
            rwg.generateWorld(finalWorldFrame);
            avatarPosition = Avatar.getPosition();
            readMovement(input, i, str, finalWorldFrame);
        } else if (input.charAt(0) == 'l') {
            In reader = new In("save_data.txt");
            while (!reader.isEmpty()) {
                char c = reader.readChar();
                str.append(c);
            }
            ip = str.toString();
            finalWorldFrame = interactWithInputString(ip);
            readMovement(input, 0, str, finalWorldFrame);
        } else if (input.charAt(0) == 'q') {
            return finalWorldFrame;
        } else {
            throw new IllegalArgumentException();
        }
        return finalWorldFrame;
    }

    private void readMovement(String input, int i, StringBuilder str, TETile[][] finalWorldFrame) {
        if (input.length() - 1 > i) {
            int j = i + 1;
            if (input.charAt(input.length() - 1) == 'q') {
                while (input.charAt(j) != ':') {
                    char c = input.charAt(j);
                    str.append(c);
                    moveAvatar(c, finalWorldFrame);
                    j += 1;
                }
                String inp = str.toString();
                writeUsingFileWriter(inp);
                ip = inp;
            } else {
                while (j < input.length()) {
                    char c = input.charAt(j);
                    str.append(c);
                    moveAvatar(c, finalWorldFrame);
                    j += 1;
                }
            }
        }
    }

    private void moveAvatar(char i, TETile[][] world, Mainmenu mm) {
        int ax = avatarPosition.getX();
        int ay = avatarPosition.getY();
        switch (i) {
            case 'w':
                switchLoc(avatarPosition, new Position(ax, ay + 1), world, mm);
                break;
            case 'a':
                switchLoc(avatarPosition, new Position(ax - 1, ay), world, mm);
                break;
            case 's':
                switchLoc(avatarPosition, new Position(ax, ay - 1), world, mm);
                break;
            case 'd':
                switchLoc(avatarPosition, new Position(ax + 1, ay), world, mm);
                break;
            default:
                break;
        }
    }

    private void switchLoc(Position a, Position t, TETile[][] world, Mainmenu mm) {
        int tx = t.getX();
        int ty = t.getY();
        int ax = a.getX();
        int ay = a.getY();
        if (tx >= 0 && tx < WIDTH && ty >= 0 && ty < HEIGHT) {
            if (world[tx][ty].equals(Tileset.BEAN)) {
                world[tx][ty] = Tileset.AVATAR;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
                point += 10;
            } else if (world[tx][ty].equals(Tileset.FLOOR)) {
                world[tx][ty] = Tileset.AVATAR;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else if (world[tx][ty].equals(Tileset.WALL1)) {
                world[tx][ty] = Tileset.AVATAR1;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else if (world[tx][ty].equals(Tileset.UNLOCKED_DOOR)) {
                if (point < target) {
                    return;
                }
                world[tx][ty] = Tileset.AVATAR2;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else {
                return;
            }

            if (world[ax][ay].equals(Tileset.AVATAR)) {
                world[ax][ay] = Tileset.WALL1;
            } else if (world[ax][ay].equals(Tileset.AVATAR1)) {
                world[ax][ay] = Tileset.WALL;
            }

            if (checkWin(tx, ty, world)) {
                level += 1;
                target += (level + 8) * 10 - 20;
                mm.nextLevel(point);
                sd = sd * 2;
                String input = "n" + sd + "s";
                world = interactWithInputString(input);
                generateRandomBean(world, level + 8);
                ter.renderFrame(world);
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    str.append(c);
                }
                useKeyboard(str, world, mm);
            }
            if (checkLose(world)) {
                mm.loseGame(point);
            }
        }
    }

    private boolean checkWin(int x, int y, TETile[][] world) {
        return (point >= target) && (world[x][y].equals(Tileset.AVATAR2));
    }

    private boolean checkLose(TETile[][] world) {
        int x = avatarPosition.getX();
        int y = avatarPosition.getY();
        if (!world[x - 1][y].equals(Tileset.WALL)) {
            return false;
        }
        if (!world[x + 1][y].equals(Tileset.WALL)) {
            return false;
        }
        if (!world[x][y + 1].equals(Tileset.WALL)) {
            return false;
        }
        if (!world[x][y - 1].equals(Tileset.WALL)) {
            return false;
        }
        return true;
    }

    private void moveAvatar(char i, TETile[][] world) {
        int ax = avatarPosition.getX();
        int ay = avatarPosition.getY();
        switch (i) {
            case 'w':
                switchLoc(avatarPosition, new Position(ax, ay + 1), world);
                break;
            case 'a':
                switchLoc(avatarPosition, new Position(ax - 1, ay), world);
                break;
            case 's':
                switchLoc(avatarPosition, new Position(ax, ay - 1), world);
                break;
            case 'd':
                switchLoc(avatarPosition, new Position(ax + 1, ay), world);
                break;
            default:
                break;
        }
    }

    private void switchLoc(Position a, Position t, TETile[][] world) {
        int tx = t.getX();
        int ty = t.getY();
        int ax = a.getX();
        int ay = a.getY();
        if (tx >= 0 && tx < WIDTH && ty >= 0 && ty < HEIGHT) {
            if (world[tx][ty].equals(Tileset.BEAN)) {
                world[tx][ty] = Tileset.AVATAR;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else if (world[tx][ty].equals(Tileset.FLOOR)) {
                world[tx][ty] = Tileset.AVATAR;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else if (world[tx][ty].equals(Tileset.WALL1)) {
                world[tx][ty] = Tileset.AVATAR1;
                Avatar.changePosition(tx, ty);
                avatarPosition = new Position(tx, ty);
            } else {
                return;
            }

            if (world[ax][ay].equals(Tileset.AVATAR)) {
                world[ax][ay] = Tileset.WALL1;
            } else if (world[ax][ay].equals(Tileset.AVATAR1)) {
                world[ax][ay] = Tileset.WALL;
            }
        }
    }


    public String getIp() {
        return ip;
    }

    private void writeUsingFileWriter(String data) {
        File file = new File("./save_data.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
