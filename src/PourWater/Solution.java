package PourWater;

import java.util.Random;

/**
 * Created by naco_siren on 9/25/17.
 */
public class Solution {
    public static void main(String[] args) {
        /* Check for boundaries */
        Solution solution1 = new Solution(new int[]{1, 2, 1, 2, 1});
        int[] r0 = solution1.pourL(4, 2); // [3, 3, 2, 2, 1]

        /* More casual test cases */
        Solution solution2 = new Solution(new int[]{5, 1, 2, 3, 2, 4});
        int[] r1 = solution2.pourL(5, 3); // [5, 4, 4, 3, 2, 4]
        int[] r2 = solution2.pour(5, 3); // [Randomly distributed]

        return;
    }


    int[] histogram;
    public Solution(int[] histogram){
        this.histogram = histogram;
    }

    /**
     * We assume that every drop of water has equal chance of flowing to left or right
     *
     * @param water the amount of water drops
     * @param position the position where water pours in
     * @return an array indicating the final histogram
     */
    public int[] pour(int water, int position) {
        int len = histogram.length;
        int[] result = histogram.clone();

        Random random = new Random();
        while (water > 0) {
            int pos = position;

            if (random.nextInt(2) == 0) {
                while (pos > 0 && result[pos - 1] <= result[pos]) pos--;
            } else {
                while (pos < len - 1 && result[pos + 1] <= result[pos]) pos++;
            }

            result[pos]++;
            water--;
        }

        return result;
    }

    /**
     * We assume that every drop of water flows to the leftmost
     *
     * @param water the amount of water drops
     * @param position the position where water pours in
     * @return an array indicating the final histogram
     */
    public int[] pourL(int water, int position) {
        int[] result = histogram.clone();

        while (water > 0) {
            int pos = position;
            while (pos > 0 && result[pos - 1] <= result[pos]) {
                pos--;
            }
            result[pos]++;
            water--;
        }

        return result;
    }
}
