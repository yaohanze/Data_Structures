/* OffByOne class
 * @author Hanze Yao
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        return Math.abs(a - b) == 1;
    }
}
