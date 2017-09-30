package wizard_shortest_distance;

import java.util.*;

/**
 * Created by naco_siren on 9/29/17.
 */
public class MySolution {
    public static void main(String[] args) {
        int[][] input1 = { {1,5,9},{2,3,9},{4},{},{},{9},{},{},{},{} };
        MySolution s1 = new MySolution(input1);
        List<Integer> r1 = s1.getShortestPath(0, 9); // [0, 5, 9] !
        List<Integer> r2 = s1.getShortestPath(3, 9); // [NULL] !

        return;
    }

    int count;
    int[][] relations;
    Wizard[] wizards;

    public MySolution(int[][] relations) {
        /* Initialize the wizards */
        this.relations = relations;
        this.count = relations.length;
        this.wizards = new Wizard[count];
        for (int i = 0; i < count; i++)
            wizards[i] = new Wizard(i);

        /* Initialize the wizards' neighbors */
        for (int i = 0; i < count; i++) {
            for (int j : relations[i])
                wizards[i].neighbors.add(wizards[j]);
        }
    }

    public List<Integer> getShortestPath(int start, int end) {
        /* Maintain a HashMap to index each Wizard's distance to the source */
        HashMap<Wizard, Integer> dists = new HashMap<>();
        for (Wizard wizard : wizards)
            dists.put(wizard, Integer.MAX_VALUE);
        dists.put(wizards[start], 0);

        /* Maintain a HashMap to index the parent to each Wizard with the shortest path */
        HashMap<Wizard, Wizard> parents = new HashMap<>();
        for (Wizard wizard : wizards)
            parents.put(wizard, wizard);

        /* Do a BFS */
        Queue<Wizard> queue = new LinkedList<>();
        queue.offer(wizards[start]);
        while (queue.isEmpty() == false) {
            Wizard cur = queue.poll();

            for (Wizard neighbor : cur.neighbors) {
                int dist = (cur.id - neighbor.id) * (cur.id - neighbor.id);

                /* If "cur" provides a shorted path,
                   update the "neighbor"'s distance and parent */
                int total = dists.get(cur) + dist;
                if (total < dists.get(neighbor)) {
                    dists.put(neighbor, total);
                    parents.put(neighbor, cur);
                }
                queue.offer(neighbor); // Could use a HashSet to prune?
            }

        }

        /* Backtrack from end to start */
        LinkedList<Integer> path = new LinkedList<>();
        path.addLast(end);

        Wizard parent = parents.get(wizards[end]);
        while (parent != wizards[start]) {
            Wizard grandParent = parents.get(parent);
            if (parent != grandParent) {
                path.addFirst(parent.id);
                parent = grandParent;
            } else {
                return null;
            }
        }
        path.addFirst(start);

        return path;
    }

    class Wizard {
        int id;
        ArrayList<Wizard> neighbors;

        public Wizard(int id) {
            this.id = id;
            neighbors = new ArrayList<Wizard>();
        }

        @Override
        public String toString() {
            return "[" + id  + "]";
        }
    }
}

