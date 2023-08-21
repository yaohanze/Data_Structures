package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private AStarGraph<Vertex> input;
    private Vertex source;
    private Vertex goal;
    private double timeout;
    private double time;
    private DoubleMapPQ<Vertex> fringe;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private LinkedList<Vertex> sol;
    private double solutionW;
    private int numSE;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        this.input = input;
        source = start;
        goal = end;
        this.timeout = timeout;
        fringe = new DoubleMapPQ<Vertex>();
        distTo = new HashMap<Vertex, Double>();
        edgeTo = new HashMap<Vertex, Vertex>();
        sol = new LinkedList<Vertex>();
        solutionW = 0;
        numSE = 0;
        time = 0;
        distTo.put(source, 0.0);
        fringe.add(source, calculateP(source));
        while (fringe.size() != 0 && !fringe.getSmallest().equals(goal)) {
            time = sw.elapsedTime();
            if (time > timeout) {
                break;
            }
            Vertex p = fringe.removeSmallest();
            numSE += 1;
            List<WeightedEdge<Vertex>> rn = input.neighbors(p);
            for (WeightedEdge<Vertex> e: rn) {
                relax(e);
            }
        }
        if (fringe.size() != 0 && time <= timeout) {
            solutionW = distTo.get(goal);
            Vertex g = goal;
            while (!g.equals(source)) {
                sol.addFirst(g);
                g = edgeTo.get(g);
            }
            sol.addFirst(g);
        }
        time = sw.elapsedTime();
        if (time > timeout) {
            sol = new LinkedList<Vertex>();
            solutionW = 0;
        }
    }

    private double h(Vertex v, Vertex g) {
        return input.estimatedDistanceToGoal(v, g);
    }

    private double calculateP(Vertex v) {
        return distTo.get(v) + h(v, goal);
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q) || (distTo.get(p) + w) < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, calculateP(q));
            } else {
                fringe.add(q, calculateP(q));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        if (sol.size() > 0) {
            return SolverOutcome.SOLVED;
        } else if (time > timeout) {
            return SolverOutcome.TIMEOUT;
        } else {
            return SolverOutcome.UNSOLVABLE;
        }
    }

    @Override
    public List<Vertex> solution() {
        return sol;
    }

    @Override
    public double solutionWeight() {
        return solutionW;
    }

    @Override
    public int numStatesExplored() {
        return numSE;
    }

    @Override
    public double explorationTime() {
        return time;
    }
}
