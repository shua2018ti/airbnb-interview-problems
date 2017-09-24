import java.util.*;

/**
 * Created by naco_siren on 9/24/17.
 */
public class BuddyList {
    public static void main(String[] args) {
        BuddyList solution = new BuddyList(new int[]{1, 2, 3, 4, 5},
                new int[][]{
                        new int[]{3, 4, 6, 8, 10}, // 2
                        new int[]{1, 2, 4, 5, 8}, // 4
                        new int[]{1, 3, 6, 7, 9}, // 2
                        new int[]{1, 2, 4, 7, 11}, // 3
                        new int[]{5, 6, 7, 12, 13}  // 1
        });

        List<Integer> r1 = solution.recommend(10);
        List<Integer> r2 = solution.recommend(5);
        List<Integer> r3 = solution.recommend(2);

        return;
    }

    
    
    HashSet<Integer> _myCities;
    ArrayList<Buddy> _buddies;

    public BuddyList(int[] wishlist, int[][] buddies) {
        this._myCities = list2set(wishlist);

        this._buddies = new ArrayList<>(buddies.length);
        for (int[] list : buddies)
            this._buddies.add(new Buddy(list));

        Collections.sort(this._buddies, new Comparator<Buddy>() {
            @Override
            public int compare(Buddy o1, Buddy o2) {
                return o1._resemblance > o2._resemblance ? -1 : 1;
            }
        });
    }

    public static HashSet<Integer> list2set(int[] wishlist) {
        HashSet<Integer> set = new HashSet<>();
        for (int city : wishlist)
            set.add(city);
        return set;
    }

    public List<Integer> recommend(int k) {
        ArrayList<Integer> recommendation = new ArrayList<>(k);
        HashSet<Integer> used = new HashSet<>();

        int count = 0;
        for (Buddy buddy : _buddies) {
            for (Integer city : buddy._specialties) {
                if (count < k) {
                    if (!used.contains(city)) {
                        recommendation.add(city);
                        used.add(city);
                        count++;
                    }
                } else {
                    return recommendation;
                }
            }
        }
        return recommendation;
    }


    public class Buddy {
        HashSet<Integer> _cities;
        Double _resemblance;
        HashSet<Integer> _specialties;
        
        public Buddy(int[] wishlist) {
            this._cities = list2set(wishlist);
            this._specialties = new HashSet<>();

            /* Compute resemblance */
            int same = 0;
            for (Integer city : this._cities)
                if (_myCities.contains(city))
                    same++;
                else
                    _specialties.add(city);
            this._resemblance = same / (double) _cities.size();
        }
    }
}
