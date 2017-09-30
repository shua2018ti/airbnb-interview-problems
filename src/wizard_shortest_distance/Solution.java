package wizard_shortest_distance;

import java.util.*;

/**
 * Created by naco_siren on 9/29/17.
 */
public class Solution {
    public static void main(String[] args) {
        int[][] ids = { {1,5,9},{2,3,9},{4},{},{},{9},{},{},{},{} };
        List<List<Integer>> input1 = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            List<Integer> neighbors = new ArrayList<>();
            for (int j = 0; j < ids[i].length; j++)
                neighbors.add(ids[i][j]);
            input1.add(neighbors);
        }

        List<Integer> r1 = getShortestPath(input1, 0, 9);

        return;
    }

    public static List<Integer> getShortestPath(List<List<Integer>> relations, int source, int target) {
        /* Maintain a root array and a HashMap for indexing */
        int[] parent = new int[relations.size()];
        Map<Integer, Wizard> wizards = new HashMap<>();
        for (int i = 0; i < relations.size(); i++) {
            parent[i] = i;
            wizards.put(i, new Wizard(i));
        }

        /* Use a PriorityQueue...? */
        wizards.get(source).dist = 0;
        Queue<Wizard> queue = new PriorityQueue<>();
        queue.offer(wizards.get(source));

        while (!queue.isEmpty()) {

            Wizard cur = queue.poll();
            List<Integer> ids = relations.get(cur.id);

            for (int id : ids) {
                Wizard neighbor = wizards.get(id);
                int weight = (cur.id - neighbor.id) * (cur.id - neighbor.id);

                /* If cur wizard shortens the path to neighbor, update neighbor's dist, parent and the queue */
                if (cur.dist + weight < neighbor.dist) {
                    queue.remove(neighbor);
                    parent[neighbor.id] = cur.id;
                    neighbor.dist = cur.dist + weight;
                    queue.offer(neighbor);
                }
            }
        }

        /* Collect the shortest path */
        List<Integer> path = new ArrayList<>();
        int index = target;
        while (index != source) {
            path.add(index);
            index = parent[index];
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }
}

class Wizard implements Comparable<Wizard> {
    int id;
    int dist;
    Wizard(int id) {
        this.id = id;
        this.dist = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Wizard that) {
        return this.dist - that.dist;
    }

    @Override
    public String toString() {
        return "[" + id + "], " + dist;
    }
}
