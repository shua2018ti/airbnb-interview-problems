package menu_combination;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution {

    public static void main(String[] args) {
        Solution s1 = new Solution(new double[]{10.02, 4.02, 1.11, 2.22, 3.01, 2.00, 5.03});
        List<List<Double>> r1 = s1.getCombos(7.03);

        return;
    }

    int count;
    double[] prices;
    long[] cents;

    public Solution(double[] prices) {
        this.count = prices.length;
        this.prices = prices;

        /* Sort the prices for better pruning */
        Arrays.sort(this.prices);

        this.cents = new long[count];
        for (int i = 0; i < count; i++) {
            cents[i] = Math.round(prices[i] * 100);
        }
    }

    public List<List<Double>> getCombos(double budget) {
        /* Perform DFS */
        LinkedList<List<Double>> results = new LinkedList<>();
        LinkedList<Double> prefix = new LinkedList<>();
        dfs(0, Math.round(budget * 100), prefix, results);

        return results;
    }

    private void dfs(int index, double budget, LinkedList<Double> prefix, LinkedList<List<Double>> results){
        if (budget == 0) {
            results.add(new LinkedList<>(prefix));
            return;
        }

        /* Prune if all the prices have been considered, or the current price exceeds the remaining budget */
        if (index == count || cents[index] > budget)
            return;

        /* Option 1: Select current price */
        prefix.add(prices[index]);
        dfs(index + 1, budget - cents[index], prefix, results);

        /* Option 2: Do not select current price */
        prefix.removeLast();
        dfs(index + 1, budget, prefix, results);
    }
}
