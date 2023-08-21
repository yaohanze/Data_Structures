package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus o = c.replicate();
        assertTrue(c.energy() == 1.0);
        assertTrue(o.energy() == 1.0);
        assertFalse(o == c);
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> fullplip = new HashMap<Direction, Occupant>();
        fullplip.put(Direction.TOP, new Plip());
        fullplip.put(Direction.TOP, new Plip());
        fullplip.put(Direction.TOP, new Plip());
        fullplip.put(Direction.TOP, new Plip());

        Action actual = c.chooseAction(fullplip);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Plip());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        Action actual1 = c.chooseAction(topEmpty);
        Action expected1 = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(expected1, actual1);

        Clorus c1 = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty1 = new HashMap<Direction, Occupant>();
        topEmpty1.put(Direction.TOP, new Empty());
        topEmpty1.put(Direction.BOTTOM, new Impassible());
        topEmpty1.put(Direction.LEFT, new Impassible());
        topEmpty1.put(Direction.RIGHT, new Impassible());

        Action actual2 = c1.chooseAction(topEmpty1);
        Action expected2 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected2, actual2);

        Clorus c2 = new Clorus(0.99);

        Action actual3 = c2.chooseAction(topEmpty1);
        Action expected3 = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected3, actual3);
    }
}


