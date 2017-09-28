package sliding_puzzle;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by naco_siren on 9/26/17.
 */
public class Game {
    private int[] nums;
    private int x;
    private int y;

    public Game() {
        /* Set the numbers */
        this.nums = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = i * 3 + j;
                nums[val] = val + 1;
            }
        }

        /* Set the blank */
        nums[8] = 0;
        x = 2; y = 2;
    }

    public Game(boolean shuffle) {
        this();
        if (!shuffle) return;

        /* Shuffle and update blank coordinates */
        ArrayUtils.shuffle(nums);
        int[] blk = find(0);
        x = blk[0]; y = blk[1];
    }

    public int get(int r, int c) {
        return nums[r * 3 + c];
    }

    public void put(int r, int c, int val) {
        nums[r * 3 + c] = val;
    }

    public int[] find(int val) {
        for (int i = 0; i < 9; i++)
            if (nums[i] == val)
                return new int[]{ i / 3, i % 3 };
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder.append("[ ");
            builder.append(nums[i * 3 + 0]);
            builder.append(", ");
            builder.append(nums[i * 3 + 1]);
            builder.append(", ");
            builder.append(nums[i * 3 + 2]);
            builder.append(" ]\n");
        }
        return builder.toString().replace('0', '_');
    }
}
