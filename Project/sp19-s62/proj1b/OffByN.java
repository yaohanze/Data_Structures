/* OffByN class
 * @author Hanze Yao
 */
public class OffByN implements CharacterComparator {
    private int n;

    public OffByN(int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        return Math.abs(a - b) == n;
    }
}
