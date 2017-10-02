package round_prices;

import java.util.Arrays;

/**
 * 公司list价格分成好几个部分，但是都是整数，如果在美金是整数，
 * 到了欧洲的网页显示汇率转换之后就变成了floating point，然后要round成整数，
 * 但是全部加起来round，和单独round再加起来，结果会不一样
 *                 USD     其他货币     其他货币取整
 * # base price    100 =>  131.13   => 131
 * # cleaning fee   20 =>   26.23   => 26
 * # service fee    10 =>   13.54   => 14
 * # tax             5 =>    6.5    => 7
 * #                   =>  177.4    => 178
 * # sum           135 =>  178.93   => 179
 *
 * Abstraction:
 * Given an array of numbers A = [x1, x2, ..., xn] and T = Round(x1+x2+... +xn).
 * We want to find a way to round each element in A such that
 * after rounding we get a new array B = [y1, y2, ...., yn]
 * such that y1+y2+...+yn = T where  yi = Floor(xi) or Ceil(xi), ceiling or floor of xi.
 * We also want to minimize sum |x_i-y_i|
 *
 * Input: A = [x1, x2, ..., xn], Sum T = Round(x1+x2+... +xn)
 * Output: B = [y1, y2, ...., yn]
 *
 * Constraint #1: y1+y2+...+yn = T
 * Constraint #2: minimize sum(abs(diff(xi - yi)))
 *
 * Examples:
 * A = [1.2, 2.3, 3.4]
 * Round(1.2 + 2.3 + 3.4) = 6.9 => 7
 * 1 + 2 + 3 => 6
 *
 * 1 + 3 + 3 => 7
 * 0.2 + 0.7 + 0.4 = 1.3
 *
 * 1 + 2 + 4 => 7
 * 0.2 + 0.3 + 0.6 = 1.1
 * 所以[1,2,4]比[1,3,3]要好
 *
 * Solution:
 * - 计算 和的round（sumRound）与 round的和（roundSum）
 * - 检查其差值
 *    1. sumRound > roundSum
 *      - 说明我们roundSum小了，应该把有些原本取floor(小数部分<0.5)的取ceil，差X就要改X个
 *      - 按照小数部分从大到小排序，取小于0.5的X个用ceil，其他正常round
 *    2. sumRound < roundSum
 *      - 说明我们roundSum大了，应该把有些原本取ceil(小数部分>=0.5)的取floor，差X就要改X个
 *      - 按照小数部分从小到大排序，取大于等于0.5的X个用floor，其他正常round
 *    3. sumRound = roundSum
 *      - 不用任何操作
 */

public class RoundPrices {
    public int[] round(double[] prices) {
        if (prices == null || prices.length == 0)
            return new int[0];
        int[] res = new int[prices.length];

        double sum = 0;
        int roundSum = 0;
        Number[] numbers = new Number[prices.length];
        for (int i = 0; i < prices.length; i++) {
            numbers[i] = new Number(prices[i], i);
            sum += prices[i];
            roundSum += (int)Math.round(prices[i]);
            res[i] = (int)Math.round(prices[i]);
        }
        int sumRound = (int)Math.round(sum);

        if (sumRound == roundSum) {
            return res;
        } else if (sumRound > roundSum) {
            Arrays.sort(numbers, (a, b) -> (Double.compare(b.frac, a.frac)));
            int count = sumRound - roundSum;
            for (int i = 0; i < prices.length; i++) {
                Number num = numbers[i];
                if (num.frac < 0.5 && count > 0) {
                    res[num.index] = (int)Math.ceil(num.val);
                    count--;
                } else {
                    res[num.index] = (int)Math.round(num.val);
                }
            }
        } else {
            Arrays.sort(numbers, (a, b) -> (Double.compare(a.frac, b.frac)));
            int count = roundSum - sumRound;
            for (int i = 0; i < prices.length; i++) {
                Number num = numbers[i];
                if (num.frac >= 0.5 && count > 0) {
                    res[num.index] = (int)Math.floor(num.val);
                    count--;
                } else {
                    res[num.index] = (int)Math.round(num.val);
                }
            }
        }

        return res;
    }
}

class Number {
    double val;
    double frac;
    int index;
    Number(double val, int index) {
        this.val = val;
        this.frac = val - Math.floor(val);
        this.index = index;
    }
}

class Main {
    public static void main(String[] args) {
        RoundPrices rp = new RoundPrices();
        double[] prices1 = {1.2, 2.3, 3.4};
        int[] res1 = rp.round(prices1);
        for (int r : res1)
            System.out.print(r + " ");
        System.out.println();


        double[] prices2 = {2.5, 2.3, 3.1, 6.5};
        int[] res2 = rp.round(prices2);
        for (int r : res2)
            System.out.print(r + " ");
        System.out.println();


        double[] prices3 = {2.9, 2.3, 1.4, 3, 6};
        int[] res3 = rp.round(prices3);
        for (int r : res3)
            System.out.print(r + " ");
        System.out.println();


        double[] prices4 = {-0.4,1.3,1.3,1.3,1.3,1.3,1.3,1.3,1.3,1.3,1.3};
        int[] res4 = rp.round(prices4);
        for (int r : res4)
            System.out.print(r + " ");
        System.out.println();

        return;
  }
}
