package boggle_game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {

        char[][] m1 = new char[][]{
                new char[]{'C','T','S','E','T'},
                new char[]{'A','I','R','L','S'},
                new char[]{'P','D','A','E','S'},
                new char[]{'U','E','C','S','E'},
                new char[]{'R','O','T','E','L'}
        };
        HashSet<String> d1 = new HashSet<>();
        /* Words appear in the matrix */
        d1.add("CAP"); d1.add("AIR"); d1.add("CAR"); d1.add("ROT"); d1.add("LET"); d1.add("ACT"); d1.add("SET");
        d1.add("LESS"); d1.add("TEST"); d1.add("RACE");
        /* Words not appear in the matrix */
        d1.add("TELL"); d1.add("LOT"); d1.add("BIG"); d1.add("WIN"); d1.add("LIKE"); d1.add("HATE"); d1.add("TRY"); d1.add("FAIL");

        Solution s1 = new Solution(m1, d1);
        List<String> r1 = s1.solve(); // [CAP, RACE, ROT, TEST, ROT, LESS]
        return;
    }

    private char[][] matrix;
    private HashSet<String> dict;

    public Solution(char[][] matrix, HashSet<String> dict) {
        this.matrix = matrix.clone();
        this.dict = dict;
    }

    public List<String> solve() {
        /* Find all the words that appear in the matrix */



        /* Perform a  */



        return null;
    }

    /* Find all the words that appear in the matrix */
    private Set<Set<int[]>> findWords() {

        return null;
    }
}
