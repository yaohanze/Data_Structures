package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //Generate random string of letters of length n
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int m = rand.nextInt(26);
            char c = CHARACTERS[m];
            str.append(c);
        }
        return str.toString();
    }

    public void drawFrame(String s) {
        //Take the string and display it in the center of the screen
        //If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2, s);
        if (!gameOver) {
            StdDraw.line(0, height - 2, width, height - 2);
            Font font1 = new Font("Monaco", Font.PLAIN, 20);
            StdDraw.setFont(font1);
            String sl = "Round: " + round;
            StdDraw.text(sl.length() / 2, height - 1, sl);
            String sr = ENCOURAGEMENT[rand.nextInt(7)];
            StdDraw.text(width - sr.length() / 2, height - 1, sr);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
        }
        StdDraw.show();

    }

    public void drawBlank() {
        StdDraw.clear(Color.BLACK);
        if (!gameOver) {
            StdDraw.line(0, height - 2, width, height - 2);
            Font font1 = new Font("Monaco", Font.PLAIN, 20);
            StdDraw.setFont(font1);
            String sl = "Round: " + round;
            StdDraw.text(sl.length() / 2, height - 1, sl);
            String sr = ENCOURAGEMENT[rand.nextInt(7)];
            StdDraw.text(width - sr.length() / 2, height - 1, sr);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //Display each character in letters, making sure to blank the screen between letters
        int length = letters.length();
        for (int i = 0; i < length; i++) {
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            StdDraw.pause(1000);
            drawBlank();
            StdDraw.pause(500);
        }
        playerTurn = true;
        drawBlank();
    }

    public String solicitNCharsInput(int n) {
        //Read n letters of player input
        StringBuilder str = new StringBuilder();
        while (str.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                str.append(input);
                drawFrame(str.toString());
            }
        }
        return str.toString();
    }

    public void startGame() {
        //Set any relevant variables before the game starts
        //Establish Engine loop
        round = 1;
        gameOver = false;
        while (!gameOver) {
            String s = "Round: " + round;
            playerTurn = false;
            drawFrame(s);
            StdDraw.pause(1000);
            String randomString = generateRandomString(round);
            flashSequence(randomString);
            String inputString = solicitNCharsInput(round);
            StdDraw.pause(500);
            if (!inputString.equals(randomString)) {
                gameOver = true;
            }
            round += 1;
        }
        int r = round - 1;
        drawFrame("Game Over! You made it to round: " + r);
    }

}
