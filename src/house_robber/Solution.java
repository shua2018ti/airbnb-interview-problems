package house_robber;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution {

    public static void main(String[] args) {
        int r1 = rob(new int[]{1, 4, 2, 8, 5, 7});

        return;
    }

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev2 = 0, prev1 = 0;
        for (int i = 0; i < nums.length; i++) {
            int cur = Math.max(prev2 + nums[i], prev1);

            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }
}
