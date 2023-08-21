package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class RandomWorldGenerator {
    private Random random;
    private ArrayList<Room> rooms;
    private TETile[][] world;
    private int width;
    private int height;
    private int cover;

    public RandomWorldGenerator(TETile[][] world, Random random) {
        this.random = random;
        this.world = world;
        this.rooms = new ArrayList<>();
        width = world.length;
        height = world[0].length;
        cover = 0;
    }

    private Room randomFirstRoom(int w, int h) {
        Position a = Position.randomPosition(random, world);
        Position b = Position.randomPosition(random, world);
        while (b.equals(a)) {
            b = Position.randomPosition(random, world);
        }
        Position.diagonalization(a, b);
        Room r = new Room(world, a, b);
        while (r.getWidth() > w || r.getHeight() > h) {
            b = Position.randomPosition(random, world);
            while (b.equals(a)) {
                b = Position.randomPosition(random, world);
            }
            Position.diagonalization(a, b);
            r = new Room(world, a, b);
        }
        return r;
    }

    private void generateUpNeighbor(Room r) {
        if (shouldStop()) {
            return;
        }
        int w = r.getWidth();
        int urY = r.getUr().getY();
        double ratio = (double) (height - urY) / height;
        if (height - urY <= 3) {
            return;
        }
        if (!neighborOrnot(ratio)) {
            return;
        }
        if (w == 1) {
            generateUpRoom(r);
        } else {
            generateUpHall(r);
        }
    }

    private void generateLeftNeighbor(Room r) {
        if (shouldStop()) {
            return;
        }
        int h = r.getHeight();
        int llX = r.getLl().getX();
        double ratio = (double) llX / width;
        if (llX <= 4) {
            return;
        }
        if (!neighborOrnot(ratio)) {
            return;
        }
        if (h == 1) {
            generateLeftRoom(r);
        } else {
            generateLeftHall(r);
        }
    }

    private void generateBottomNeighbor(Room r) {
        if (shouldStop()) {
            return;
        }
        int w = r.getWidth();
        int llY = r.getLl().getY();
        double ratio = (double) llY / height;
        if (llY <= 3) {
            return;
        }
        if (!neighborOrnot(ratio)) {
            return;
        }
        if (w == 1) {
            generateBottomRoom(r);
        } else {
            generateBottomHall(r);
        }
    }

    private void generateRightNeighbor(Room r) {
        if (shouldStop()) {
            return;
        }
        int h = r.getHeight();
        int urX = r.getUr().getX();
        double ratio = (double) (width - urX) / width;
        if (width - urX <= 4) {
            return;
        }
        if (!neighborOrnot(ratio)) {
            return;
        }
        if (h == 1) {
            generateRightRoom(r);
        } else {
            generateRightHall(r);
        }
    }

    private boolean neighborOrnot(double ratio) {
        if (ratio > 0.7) {
            return true;
        }
        int bound = (int) (ratio * 10);
        int i = RandomUtils.uniform(random, 0, 10);
        if (i < bound) {
            return true;
        }
        return false;
    }

    private void generateUpRoom(Room r) {
        int urY = r.getUr().getY();
        int urX = r.getUr().getX();
        int heightLimit = height - urY - 1;
        if (heightLimit <= 1) {
            return;
        }
        int h = RandomUtils.uniform(random, 1, heightLimit);
        int w = RandomUtils.uniform(random, 2, width / 8);
        while (h > height / 2) {
            h = RandomUtils.uniform(random, 1, heightLimit);
        }
        Position ll = new Position(RandomUtils.uniform(random, urX - w + 1, urX + 1), urY + 1);
        Position ur = new Position(ll.getX() + w - 1, ll.getY() + h);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 1, heightLimit);
            w = RandomUtils.uniform(random, 2, width / 8);
            while (h > height / 2) {
                h = RandomUtils.uniform(random, 1, heightLimit);
            }
            ll = new Position(RandomUtils.uniform(random, urX - w + 1, urX + 1), urY + 1);
            ur = new Position(ll.getX() + w - 1, ll.getY() + h);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateLeftNeighbor(result);
        generateRightNeighbor(result);
    }

    private void generateUpHall(Room r) {
        int urY = r.getUr().getY();
        int urX = r.getUr().getX();
        int llX = r.getLl().getX();
        int heightLimit = height - urY - 1;
        if (heightLimit <= 2) {
            return;
        }
        int h = RandomUtils.uniform(random, 2, heightLimit);
        Position ll = new Position(RandomUtils.uniform(random, llX, urX + 1), urY + 1);
        Position ur = new Position(ll.getX(), ll.getY() + h - 1);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 2, heightLimit);
            ll = new Position(RandomUtils.uniform(random, llX, urX + 1), urY + 1);
            ur = new Position(ll.getX(), ll.getY() + h - 1);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        //rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateLeftNeighbor(result);
        generateRightNeighbor(result);
    }

    private void generateLeftRoom(Room r) {
        int llX = r.getLl().getX();
        int llY = r.getLl().getY();
        int widthLimit = llX;
        if (widthLimit <= 1) {
            return;
        }
        int h = RandomUtils.uniform(random, 2, height / 2);
        int w = RandomUtils.uniform(random, 1, widthLimit);
        while (w > width / 8) {
            w = RandomUtils.uniform(random, 1, widthLimit);
        }
        Position ur = new Position(llX - 1, RandomUtils.uniform(random, llY, llY + h));
        Position ll = new Position(ur.getX() - w + 1, ur.getY() - h + 1);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 2, height / 2);
            w = RandomUtils.uniform(random, 1, widthLimit);
            while (w > width / 8) {
                w = RandomUtils.uniform(random, 1, widthLimit);
            }
            ur = new Position(llX - 1, RandomUtils.uniform(random, llY, llY + h));
            ll = new Position(ur.getX() - w + 1, ur.getY() - h + 1);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateLeftNeighbor(result);
        generateBottomNeighbor(result);
    }

    private void generateLeftHall(Room r) {
        int urY = r.getUr().getY();
        int llY = r.getLl().getY();
        int llX = r.getLl().getX();
        int widthLimit = llX;
        if (widthLimit <= 2) {
            return;
        }
        int w = RandomUtils.uniform(random, 2, widthLimit);
        Position ur = new Position(llX - 1, RandomUtils.uniform(random, llY, urY + 1));
        Position ll = new Position(ur.getX() - w + 1, ur.getY());
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            w = RandomUtils.uniform(random, 2, widthLimit);
            ur = new Position(llX - 1, RandomUtils.uniform(random, llY, urY + 1));
            ll = new Position(ur.getX() - w + 1, ur.getY());
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        //rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateLeftNeighbor(result);
        generateBottomNeighbor(result);
    }

    private void generateBottomRoom(Room r) {
        int llY = r.getLl().getY();
        int llX = r.getLl().getX();
        int heightLimit = llY;
        if (heightLimit <= 1) {
            return;
        }
        int h = RandomUtils.uniform(random, 1, heightLimit);
        int w = RandomUtils.uniform(random, 2, width / 8);
        while (h > height / 2) {
            h = RandomUtils.uniform(random, 1, heightLimit);
        }
        Position ur = new Position(RandomUtils.uniform(random, llX, llX + w), llY - 1);
        Position ll = new Position(ur.getX() - w + 1, ur.getY() - h + 1);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 1, heightLimit);
            w = RandomUtils.uniform(random, 2, width / 8);
            while (h > height / 2) {
                h = RandomUtils.uniform(random, 1, heightLimit);
            }
            ur = new Position(RandomUtils.uniform(random, llX, llX + w), llY - 1);
            ll = new Position(ur.getX() - w + 1, ur.getY() - h + 1);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateRightNeighbor(result);
        generateLeftNeighbor(result);
        generateBottomNeighbor(result);
    }

    private void generateBottomHall(Room r) {
        int llY = r.getLl().getY();
        int llX = r.getLl().getX();
        int urX = r.getUr().getX();
        int heightLimit = llY;
        if (heightLimit <= 2) {
            return;
        }
        int h = RandomUtils.uniform(random, 2, heightLimit);
        Position ur = new Position(RandomUtils.uniform(random, llX, urX + 1), llY - 1);
        Position ll = new Position(ur.getX(), ur.getY() - h + 1);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 2, heightLimit);
            ur = new Position(RandomUtils.uniform(random, llX, urX + 1), llY - 1);
            ll = new Position(ur.getX(), ur.getY() - h + 1);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        //rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateRightNeighbor(result);
        generateLeftNeighbor(result);
        generateBottomNeighbor(result);
    }

    private void generateRightRoom(Room r) {
        int urX = r.getUr().getX();
        int urY = r.getUr().getY();
        int widthLimit = width - urX - 1;
        if (widthLimit <= 1) {
            return;
        }
        int h = RandomUtils.uniform(random, 2, height / 2);
        int w = RandomUtils.uniform(random, 1, widthLimit);
        while (w > width / 8) {
            w = RandomUtils.uniform(random, 1, widthLimit);
        }
        Position ll = new Position(urX + 1, RandomUtils.uniform(random, urY - h + 1, urY + 1));
        Position ur = new Position(ll.getX() + w - 1, ll.getY() + h - 1);
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            h = RandomUtils.uniform(random, 2, height / 2);
            w = RandomUtils.uniform(random, 1, widthLimit);
            while (w > width / 8) {
                w = RandomUtils.uniform(random, 1, widthLimit);
            }
            ll = new Position(urX + 1, RandomUtils.uniform(random, urY - h + 1, urY + 1));
            ur = new Position(ll.getX() + w - 1, ll.getY() + h - 1);
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateRightNeighbor(result);
        generateBottomNeighbor(result);
    }

    private void generateRightHall(Room r) {
        int urY = r.getUr().getY();
        int llY = r.getLl().getY();
        int urX = r.getUr().getX();
        int widthLimit = width - urX - 1;
        if (widthLimit <= 2) {
            return;
        }
        int w = RandomUtils.uniform(random, 2, widthLimit);
        Position ll = new Position(urX - 1, RandomUtils.uniform(random, llY, urY + 1));
        Position ur = new Position(ll.getX() + w - 1, ll.getY());
        Room result = new Room(world, ll, ur);
        int num = 0;
        while ((checkOverlap(rooms, r, result) || checkOutofbound(result)) && num < 10000) {
            w = RandomUtils.uniform(random, 2, widthLimit);
            ll = new Position(urX - 1, RandomUtils.uniform(random, llY, urY + 1));
            ur = new Position(ll.getX() + w - 1, ll.getY());
            result = new Room(world, ll, ur);
            num += 1;
        }
        if (num == 10000) {
            return;
        }
        //rooms.add(result);
        cover += result.size();
        result.drawRoom();
        generateUpNeighbor(result);
        generateRightNeighbor(result);
        generateBottomHall(result);
    }

    private boolean shouldStop() {
        double ratio = (double) cover / (width * height);
        if (ratio > 0.6) {
            return true;
        }
        return false;
    }

    private boolean checkOverlap(ArrayList<Room> rl, Room r, Room n) {
        for (Room rm: rl) {
            if (rm.equals(r)) {
                continue;
            }
            if (rm.isOverlap(n)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkOutofbound(Room r) {
        int llx = r.getLl().getX();
        int lly = r.getLl().getY();
        int urx = r.getUr().getX();
        int ury = r.getUr().getY();
        if (!((llx > 0) && (llx < width - 1))) {
            return true;
        }
        if (!((lly > 0) && (lly < height - 1))) {
            return true;
        }
        if (!((urx > 0) && (urx < width - 1))) {
            return true;
        }
        if (!((ury > 0) && (ury < height - 1))) {
            return true;
        }
        return false;
    }

    public void generateWorld(TETile[][] w) {
        fillNoting(w);
        Room start = randomFirstRoom(width / 8, height / 2);
        start.drawRoom();
        cover += start.size();
        rooms.add(start);
        generateUpNeighbor(start);
        generateLeftNeighbor(start);
        generateBottomNeighbor(start);
        generateRightNeighbor(start);
        Wall.gennerateWall(w);
        Door.generateDoor(w);
        Avatar.generateAvatar(w);
    }

    private void fillNoting(TETile[][] w) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                w[x][y] = Tileset.NOTHING;
            }
        }
    }

    //public static void main(String[] args) {
        //TERenderer ter = new TERenderer();
        //int width = 80;
        //int height = 30;
        //ter.initialize(width, height);
        //TETile[][] world = new TETile[width][height];
        //long x = 938576104;
        //Random random = new Random(x);
        //RandomWorldGenerator rwg = new RandomWorldGenerator(world, random);
        //rwg.generateWorld(world);
        //ter.renderFrame(world);
    //}
}
