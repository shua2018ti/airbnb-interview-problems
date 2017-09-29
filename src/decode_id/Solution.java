package decode_id;

import java.util.ArrayList;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution {
    public static Integer decode(String testEncStr) {
        return testEncStr.equals("kljJJ324hjkS_") ? 848662 : null;
    }

    public static void main(String[] args) {
        Solution s0 = new Solution("kljJJ324hjkS_");
        Integer r0 = s0.decodeFind();

        Solution s1 = new Solution("kljjj324hjks_");
        Integer r1 = s1.decodeFind();

        Solution s2 = new Solution("kljjj324hjkd_");
        Integer r2 = s2.decodeFind();

        return;
    }
    String badEncStr;

    public Solution(String badEncStr) {
        this.badEncStr = badEncStr;
    }

    ArrayList<String> candidates;
    public Integer decodeFind() {
        if (badEncStr == null || badEncStr.length() == 0)
            return -1;
        badEncStr = badEncStr.toLowerCase();

        /* Initialize the ArrayList */
        this.candidates = new ArrayList<>();
        candidates.add(badEncStr);

        for (int i = 0; i < badEncStr.length(); i++) {
            /* Pass the non-alphabetic characters */
            if (Character.isAlphabetic(badEncStr.charAt(i)) == false)
                continue;

            /* Adjust the character to upper case */
            int size = candidates.size();
            for (int j = 0; j < size; j++) {
                StringBuffer sb = new StringBuffer(candidates.get(j));
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
                candidates.add(sb.toString());
            }
        }

        /* Test all the candidates against test method */
        for (String cand : candidates) {
            Integer id = decode(cand);
            if (id != null) return id;
        }
        return null;
    }
}
