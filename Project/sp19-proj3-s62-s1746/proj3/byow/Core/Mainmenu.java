package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.In;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

public class Mainmenu {
    private int width;
    private int height;
    private String input;
    private int level;
    private int point;

    public Mainmenu(int width, int height) {
        this.width = width;
        this.height = height;
        level = 1;
        point = 0;
        input = "";
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height * 3 / 4, "CS61B: DUNGEON SURVIVE");
        Font font1 = new Font("Monaco", Font.PLAIN, 15);
        StdDraw.setFont(font1);
        StdDraw.text(width / 2, height / 2 + 2, "New Game (n)");
        StdDraw.text(width / 2, height / 2, "Load Game (l)");
        StdDraw.text(width / 2, height / 2 - 2, "Replay (r)");
        StdDraw.text(width / 2, height / 2 - 4, "Name your Avatar (a)");
        StdDraw.text(width / 2, height / 2 - 6, "Help (h)");
        StdDraw.text(width / 2, height / 2 - 8, "Quit (q)");
        StdDraw.show();
    }

    public void help() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height * 8 / 9, "Game Background and Help");
        Font font1 = new Font("Monaco", Font.PLAIN, 15);
        StdDraw.setFont(font1);
        StdDraw.text(width / 2, height / 2 + 12,
                "You are trapped in a drak dungeon.");
        StdDraw.text(width / 2, height / 2 + 10,
                "Your goal is collect enough beans to survive.");
        StdDraw.text(width / 2, height / 2 + 8,
                "And get to the unlocked door to next level.");
        StdDraw.text(width / 2, height / 2 + 6,
                "You can control your avatar using w, a, s, d.");
        StdDraw.text(width / 2, height / 2 + 4,
                "More beans will show up as you level up.");
        StdDraw.text(width / 2, height / 2 + 2,
                "Collecting each bean will earn you 10 points.");
        StdDraw.text(width / 2, height / 2,
                "All of earnable point will be added to target point at each level.");
        StdDraw.text(width / 2, height / 2 - 2,
                "You can't get to next level without earning enough points");
        StdDraw.text(width / 2, height / 2 - 4,
                "Floor that you have stepped on will become dangerous.");
        StdDraw.text(width / 2, height / 2 - 6,
                "It will become wall after next time you step on it.");
        StdDraw.text(width / 2, height / 2 - 8,
                "You will lose the game if you are surrounded by walls");
        StdDraw.text(width / 2, height / 2 - 10,
                "So think carefully before making any move.");
        StdDraw.text(width / 2, height / 2 - 12,
                "Only the bravest and smartest player can survive the dungeon.");
        StdDraw.text(width / 2, height / 2 - 14,
                "Are you ready for the challenge?");
        Font font2 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font2);
        StdDraw.text(width / 2, height / 2 - 18,
                "Back to main menu (b)");
        StdDraw.show();
        StringBuilder str = new StringBuilder();
        while (str.length() < 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                str.append(c);
            }
        }
        if (str.toString().equals("b")) {
            return;
        }
    }

    public String newGame(StringBuilder str) {
        StdDraw.clear(Color.BLACK);
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(width / 2, height * 3 / 4, "Please input a seed");
        Font font3 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font3);
        StdDraw.text(width / 2, height / 4, "Start game (s)");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setFont(font2);
                StdDraw.text(width / 2, height * 3 / 4, "Please input a seed");
                StdDraw.setFont(font3);
                StdDraw.text(width / 2, height / 4, "Start game (s)");
                char ip = StdDraw.nextKeyTyped();
                str.append(ip);
                if (ip == 's') {
                    break;
                }
                StdDraw.text(width / 2, height / 2, str.toString().substring(1));
                StdDraw.show();
            }
        }
        return str.toString();
    }

    public void startGame() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "Level " + level + ". Game Start!");
        StdDraw.show();
        StdDraw.pause(1000);
    }

    public void nextLevel(int p) {
        point = p;
        level += 1;
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "You made it! Level " + level + ". Game Start!");
        Font font1 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font1);
        StdDraw.text(width / 2, height / 4, "You now have " + point + " point(s).");
        StdDraw.show();
        StdDraw.pause(1500);
    }

    public void loseGame(int p) {
        point = p;
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "You Lost! Made it to Level " + level + ".");
        Font font1 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font1);
        StdDraw.text(width / 2, height / 4, "You earned " + point + " point(s).");
        StdDraw.text(width / 2, height / 8, "Quit game (q)");
        StdDraw.show();
        StringBuilder str = new StringBuilder();
        while (str.length() < 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                str.append(c);
            }
        }
        if (str.toString().equals("q")) {
            System.exit(0);
        }
    }

    public String nameAvatar(StringBuilder str) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height * 3 / 4, "Input the name of Avatar");
        Font font1 = new Font("Monaco", Font.PLAIN, 15);
        StdDraw.setFont(font1);
        StdDraw.text(width / 2, height / 4, "Save (s)");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setFont(font);
                StdDraw.text(width / 2, height * 3 / 4, "Input the name of Avatar");
                StdDraw.setFont(font1);
                StdDraw.text(width / 2, height / 4, "Save (s)");
                char c = StdDraw.nextKeyTyped();
                if (c == 's') {
                    break;
                }
                str.append(c);
                StdDraw.text(width / 2, height / 2, str.toString().substring(1));
                StdDraw.show();
            }
        }
        return str.toString().substring(1);
    }

    public void endGameSave(String ip) {
        writeUsingFileWriter(ip);
        System.exit(0);
    }

    public String loadGame() {
        In reader = new In("save_data.txt");
        StringBuilder str = new StringBuilder();
        while (!reader.isEmpty()) {
            char c = reader.readChar();
            str.append(c);
        }
        input = str.toString();
        return input;
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

    public String getInput() {
        return input;
    }
}
