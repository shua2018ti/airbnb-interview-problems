package pyramid_transition;

/**
 * 给你一个字符对的转换matrix，表示这个字符对会转化成一个字符，但是有的字符对可能有多个能够转化成的字符。
 * 再给你一个rule，代表若干合法的结果
 * 多次询问，每次一个字符串如果有一个方法能够走到合法状态就算是YES，否则NO
 * http://www.1point3acres.com/bbs/thread-146537-1-1.html
 *
 *   A   B   C  D
 * A AC  CD  D  B
 * B B   C   A  CD
 * C A   C   D  B
 * D BC  D   A  C
 *
 * Assum input to be:
 * A,A,AC
 * A,B,CD
 * A,C,D
 * ...
 *
 * 多次call checkInput
 */

import java.util.*;

public class PyramidTransition {
    public static void main(String[] args) {
        String[] input1 = {
                "A,A,AC", "A,B,CD", "A,C,D", "A,D,B",
                "B,A,B", "B,B,C", "B,C,A", "B,D,CD",
                "C,A,A", "C,B,C", "C,C,D", "C,D,B",
                "D,A,BC", "D,B,D", "D,C,A", "D,D,C"
        };
        PyramidTransition pt1 = new PyramidTransition(input1, "CD");

        boolean r1 = pt1.checkInput("ABCD");
        boolean r2 = pt1.checkInput("AACC");
        boolean r3 = pt1.checkInput("AAAA");

        return;
    }

    private Set<Character> rightfulHeirs;
    private Map<Character, Map<Character, Set<Character>>> transitionDict;
    private Map<String, Boolean> cache;

    public PyramidTransition(String[] lines, String heirs) {
        /* Use a set to present all the rightful heirs */
        rightfulHeirs = new HashSet<>();
        for (int i = 0; i < heirs.length(); i++)
            rightfulHeirs.add(heirs.charAt(i));

        /* Initialize the transition dictionary */
        transitionDict = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            char father = parts[0].charAt(0), mother = parts[1].charAt(0);

            /* Prepare left */
            if (!transitionDict.containsKey(father))
                transitionDict.put(father, new HashMap<>());

            /* Prepare right (nested HashMap) */
            Set<Character> sons = new HashSet<>();
            for (int i = 0; i < parts[2].length(); i++)
                sons.add(parts[2].charAt(i));
            transitionDict.get(father).put(mother, sons);
        }

        /* Initialize the cache */
        cache = new HashMap<>();
    }

    /**
     * Check if the input string can result in any final rule
     */
    public boolean checkInput(String input) {
        /* If cached before, fetch result directly */
        if (cache.containsKey(input))
            return cache.get(input);

        /* If walks to the top, check if it is a rightful heir */
        if (input.length() == 1) {
            cache.put(input, rightfulHeirs.contains(input.charAt(0)));
            return cache.get(input);
        }

        /* If still not top, perform a BFS */
        List<String> nextGeneration = new ArrayList<>();
        breedNextGen(nextGeneration, input, 0, new StringBuilder());

        for (String nextInput : nextGeneration) {
            if (checkInput(nextInput)) {
                cache.put(input, true);
                return true;
            }
        }

        /* Cache current input's result */
        cache.put(input, false);
        return false;
    }

    /**
     * Use the parents to breed next generations
     */
    private void breedNextGen(List<String> res, String input, int start, StringBuilder sb) {
        if (start == input.length() - 1) {
            res.add(sb.toString());
            return;
        }

        char left = input.charAt(start), right = input.charAt(start + 1);

        for (char c : transitionDict.get(left).get(right)) {
            sb.append(c);
            breedNextGen(res, input, start + 1, sb);
            sb.setLength(sb.length() - 1);
        }
    }
}


