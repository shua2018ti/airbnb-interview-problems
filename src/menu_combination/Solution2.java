package menu_combination;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution2 {

    public static void main(String[] args) {
        Solution2 s1 = new Solution2(new double[]{10.02, 4.02, 1.11, 2.22, 3.01, 2.00, 5.03});
        List<List<Double>> r1 = s1.getCombos(7.03);

        return;
    }

    int count;
    double[] prices;

    public Solution2(double[] prices) {
        this.count = prices.length;
        this.prices = prices;
        Arrays.sort(this.prices);
    }

    public List<List<Double>> getCombos(double budget) {
        LinkedList<List<Double>> results = new LinkedList<>();

        /* Initialize the candidates and their sums */
        LinkedList<LinkedList<Double>> candidates = new LinkedList<>();
        LinkedList<Double> sums = new LinkedList<>();

        /* Add the first empty candidate as seed */
        LinkedList<Double> candidate = new LinkedList<>();
        candidates.add(candidate);
        sums.add(0.0);

        /* Find all combinations */
        for (int i = 0; i < count; i++) {
            int size = candidates.size();
            for (int j = 0; j < size; j++) {
                LinkedList<Double> cand = candidates.get(j);
                double s = sums.get(j);

                /* If sum equals to budget, add to results;
                   If sum less than budget, keep finding combinations */
                if (Math.abs(budget - s) < 0.0000001) {
                    results.add(new LinkedList<>(cand));

                    /* Update */
                    candidates.remove(j);
                    sums.remove(j);

                } else if (budget - s > 0.0000001) {
                    /* Add current price into the candidate */
                    LinkedList<Double> newCand = new LinkedList<>(cand);
                    newCand.add(prices[i]);

                    /* Update */
                    candidates.add(newCand);
                    sums.add(s + prices[i]);
                }
            }
        }

        return results;
    }


}
