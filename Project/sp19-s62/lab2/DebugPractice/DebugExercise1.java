/**
 * Exercise for learning how the debug, breakpoint, and step-into
 * feature work.
 */
public class DebugExercise1 {
    public static long divideThenRound(double top, double bottom) {
        double quotient = top / bottom;
        long result = Math.round(quotient);
        return result;
    }

    public static void main(String[] args) {
        double t = 10;
        double b = 2;
        long result = divideThenRound(t, b);
        System.out.println("round(" + t + "/" + b + ")=" + result);
        double t2 = 9;
        double b2 = 4;
        long result2 = divideThenRound(t2, b2);
        System.out.println("round(" + t2 + "/" + b2 + ")=" + result2);

        double t3 = 3;
        double b3 = 4;
        long result3 = divideThenRound(t3, b3);
        System.out.println("round(" + t3 + "/" + b3 + ")=" + result3);
    }
}
