/* Test for Palindrome
 * @author Hanze Yao
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("NOON"));
        assertTrue(palindrome.isPalindrome("%%"));
        assertTrue(palindrome.isPalindrome("%v%"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertFalse(palindrome.isPalindrome("Racecar"));
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("A", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("cod", cc));
        assertTrue(palindrome.isPalindrome("FlAkE", cc));
        assertTrue(palindrome.isPalindrome("teds", cc));
        assertTrue(palindrome.isPalindrome("%&", cc));
        assertTrue(palindrome.isPalindrome("&flake%", cc));
        assertTrue(palindrome.isPalindrome("FLAKE", cc));
        assertTrue(palindrome.isPalindrome("CD", cc));
        assertTrue(palindrome.isPalindrome("ab", cc));
        assertFalse(palindrome.isPalindrome("ad", cc));
        assertFalse(palindrome.isPalindrome("Flake", cc));
        assertFalse(palindrome.isPalindrome("FLake", cc));
        assertFalse(palindrome.isPalindrome("coD", cc));
        assertFalse(palindrome.isPalindrome("Ab", cc));
        CharacterComparator offBy5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", offBy5));
        assertTrue(palindrome.isPalindrome("A", offBy5));
        assertTrue(palindrome.isPalindrome("AF", offBy5));
        assertTrue(palindrome.isPalindrome("af", offBy5));
        assertTrue(palindrome.isPalindrome("linking", offBy5));
        assertTrue(palindrome.isPalindrome("LINKING", offBy5));
        assertTrue(palindrome.isPalindrome("%*", offBy5));
        assertFalse(palindrome.isPalindrome("Af", offBy5));
        assertFalse(palindrome.isPalindrome("Linking", offBy5));
        assertFalse(palindrome.isPalindrome("LInking", offBy5));
        assertFalse(palindrome.isPalindrome("flake", offBy5));
    }
}
