package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        double originalEnergy = this.energy();
        this.energy = originalEnergy / 2;
        Clorus offspring = new Clorus(originalEnergy / 2);
        return offspring;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.addLast(key);
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key) != null && neighbors.get(key).name().equals("plip")) {
                anyPlip = true;
                plipNeighbors.addLast(key);
            }
        }

        if (anyPlip == true) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }

        if (energy() >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
