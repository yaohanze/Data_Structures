/* Autograder with random test for ArrayDeque.
 * @author Hanze Yao
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        for (int i = 0; i < 1000; i += 1) {
            int random = StdRandom.uniform(4);
            if (random == 0) {
                student.addFirst(i);
                solution.addFirst(i);
                System.out.println("addFirst(" + i + ")");
            } else if (random == 1) {
                student.addLast(i);
                solution.addLast(i);
                System.out.println("addLast(" + i + ")");
            } else if (random == 2) {
                if (student.size() > 0 && solution.size() > 0) {
                    Integer first_student = student.removeFirst();
                    Integer first_solution = solution.removeFirst();
                    System.out.println("removeFirst()");
                    assertEquals("removeFirst()", first_solution, first_student);
                }
            } else {
                if (student.size() > 0 && solution.size() > 0) {
                    Integer last_student = student.removeLast();
                    Integer last_solution = solution.removeLast();
                    System.out.println("removeLast()");
                    assertEquals("removeLast()", last_solution, last_student);
                }
            }
        }
        System.out.println("get()");
        assertEquals("size()", solution.size(), student.size());
        for (int j = 0; j < solution.size(); j += 1) {
            System.out.println("get(" + j + ")");
            assertEquals("get(" + j + ")", solution.get(j), student.get(j));
        }
    }
}
