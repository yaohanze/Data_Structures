/* Palindrome class
 * @author Hanze Yao
 */
public class Palindrome {
    /** From Stackoverflow, I learn how to get the i-th character in a String.*/
    public Deque<Character> wordToDeque(String word) {
        Deque d = new LinkedListDeque();
        int i = 0;
        while (i < word.length()) {
            d.addLast(word.charAt(i));
            i += 1;
        }
        return d;
    }

    /** From Stackoverflow, I learn how to check if two strings are the same.*/
    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        char a = d.removeFirst();
        char b = d.removeLast();
        String left = "";
        int j = d.size();
        for (int i = 0; i < j; i++) {
            left += d.removeFirst();
        }
        return a == b && isPalindrome(left);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        char a = d.removeFirst();
        char b = d.removeLast();
        String left = "";
        int j = d.size();
        for (int i = 0; i < j; i++) {
            left += d.removeFirst();
        }
        return cc.equalChars(a, b) && isPalindrome(left, cc);
    }
}
