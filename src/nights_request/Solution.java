package nights_request;

/**
 * Created by naco_siren on 9/25/17.
 */
public class Solution {
    public static void main(String[] args) {
        int r0 = maxNights2(new int[]{2}); // [2]
        int r1 = maxNights2(new int[]{1, 2, 3}); // [4]
        int r2 = maxNights2(new int[]{3, 6, 4}); // [7]
        int r3 = maxNights2(new int[]{5, 1, 2, 6}); // [11]
        int r4 = maxNights2(new int[]{5, 1, 1, 5}); // [10]
        int r5 = maxNights2(new int[]{4, 10, 3, 1, 5}); // [15]
        int r6 = maxNights2(new int[]{5, 1, 2, 6, 20, 2}); // [27]

        return;
    }

    public static int maxNights(int[] nights){
        if (nights == null) return 0;
        int len = nights.length;
        if (len == 1) return nights[0];

        int prevNeg = 0, prevPos = nights[0];
        for (int i = 1; i < len; i++) {
            int curPos = prevNeg + nights[i];

            prevNeg = Math.max(prevNeg, prevPos);
            prevPos = curPos;
        }

        return Math.max(prevNeg, prevPos);
    }

    public static int maxNights2(int[] nights) {
        if (nights == null) return 0;
        int len = nights.length;
        if (len == 1) return nights[0];

        int prev = 0, last = 0;
        for (int val : nights) {
            int cur = Math.max(prev + val, last);

            prev = last; last = cur;
        }

        return last;
    }

}
