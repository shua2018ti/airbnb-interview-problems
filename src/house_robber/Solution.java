package house_robber;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution {

    public static void main(String[] args) {
        int r1 = rob(new int[]{1, 4, 2, 8, 5, 7});

        int r2 = rob2(new int[]{4, 2, 8, 5, 7});

        return;
    }

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev = 0, last = 0;
        for (int i = 0; i < nums.length; i++) {
            int cur = Math.max(prev + nums[i], last);

            prev = last;
            last = cur;
        }
        return last;
    }

    public static int rob2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        /* Rob the first house */
        int prev1 = nums[0], last1 = prev1;

        /* Do not rob the first house */
        int prev2 = 0, last2 = nums[1];

        for (int i = 2; i < nums.length; i++) {
            int cur1 = Math.max(prev1 + nums[i], last1);
            prev1 = last1; last1 = cur1;

            int cur2 = Math.max(prev2 + nums[i], last2);
            prev2 = last2; last2 = cur2;
        }

        return Math.max(prev1, last2); // We've robbed the first house, therefore "last1" can not be robbed.
    }
}
