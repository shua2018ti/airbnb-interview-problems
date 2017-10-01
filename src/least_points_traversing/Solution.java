package least_points_traversing;

import java.util.*;

/**
 * Created by naco_siren on 9/25/17.
 */
public class Solution {
    public static void main(String[] args) {
        /*
         * Please forgive my poor graph presentation.
         *
         * -----------
         * 1 -> 2, 3
         * 2 -> 3
         *
         * Ans: 1
         * -----------
         * 4 -> 5
         *
         * Ans: 4
         * -----------
         * 6 -> 7
         * 7 -> 8
         * 8 -> 9
         * 9 -> 8, 10
         * 10 -> 6
         *
         * 7 -> 11, 12
         * 11 -> 12, 13
         * 12 -> 13
         *
         * Ans: 6/7/8/9/10
         * -----------
         * 14 -> 15, 16
         * 15 -> 16
         * 15 -> 14
         *
         * Ans: 14/15
         * -----------
         *
         * Expected output: 1, 4, 6/7/8/9/10, 14/15
         */

        Node n1 = new Node(1);
        Node n2 = new Node(2); n1.children.add(n2);
        Node n3 = new Node(3); n1.children.add(n3); n2.children.add(n3);
        Node n4 = new Node(4);
        Node n5 = new Node(5); n4.children.add(n5);
        Node n6 = new Node(6);
        Node n7 = new Node(7); n6.children.add(n7);
        Node n8 = new Node(8); n7.children.add(n8);
        Node n9 = new Node(9); n8.children.add(n9); n9.children.add(n8);
        Node n10 = new Node(10); n9.children.add(n10); n10.children.add(n6);
        Node n11 = new Node(11); n7.children.add(n11);
        Node n12 = new Node(12); n7.children.add(n12); n11.children.add(n12);
        Node n13 = new Node(13); n12.children.add(n13); n11.children.add(n13);
        Node n14 = new Node(14);
        Node n15 = new Node(15); n14.children.add(n15); n15.children.add(n14);
        Node n16 = new Node(16); n14.children.add(n16); n15.children.add(n16);


        HashSet<Node> nodes = new HashSet<>();
        nodes.add(n1); nodes.add(n2); nodes.add(n3); nodes.add(n4);
        nodes.add(n5); nodes.add(n6); nodes.add(n7); nodes.add(n8);
        nodes.add(n9); nodes.add(n10); nodes.add(n11); nodes.add(n12);
        nodes.add(n13); nodes.add(n14); nodes.add(n15); nodes.add(n16);

        Solution solution = new Solution(nodes);
        List<Node> results = solution.solve();


        return;
    }

    HashSet<Node> _nodes;

    /**
     * Constructor
     * @param nodes all the vertices in the graph
     */
    public Solution (HashSet<Node> nodes) {
        this._nodes = nodes;
    }

    HashSet<Node> _candidates;
    LinkedList<Node> _results;

    /**
     * Main solution method
     *
     * @return A list of nodes that can traverse the graph
     */
    public List<Node> solve() {
        /* Initialize the candidates and results */
        this._candidates = new HashSet<>(this._nodes);
        this._results = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();

        /* Calculate the in-degrees of each node */
        HashMap<Node, Integer> inDegrees = new HashMap<>(_nodes.size());
        for (Node node : _nodes)
            inDegrees.put(node, 0);
        for (Node node : _nodes)
            for (Node child : node.children)
                inDegrees.put(child, inDegrees.get(child) + 1);

        /* Find all tree roots and add them into queue */
        LinkedList<Node> roots = new LinkedList<>();
        for (Node node : _nodes)
            if (inDegrees.get(node) == 0)
                roots.add(node);
        this._results.addAll(roots);

        /* Perform BFS to remove all the children of the roots from candidates */
        Queue<Node> rmQueue = new LinkedList<>();
        for (Node node : roots) rmQueue.offer(node);
        while (rmQueue.isEmpty() == false) {
            Node node = rmQueue.poll();

            for (Node child : node.children)
                if (visited.contains(child) == false)
                    rmQueue.offer(child);

            _candidates.remove(node);
            visited.add(node);
        }

        /* By now there should be only several connected components */

        /* Cluster candidates into groups */
        HashMap<Node, Group> groups = new HashMap<>(_candidates.size());
        for (Node cand : _candidates) {
            /* Only visit each node once */
            if (visited.contains(cand)) continue;

            /* Collect each candidate's tree as a group by BFS */
            Group group = new Group(cand);
            Queue<Node> queue = new LinkedList<>();
            queue.offer(cand);
            while (queue.isEmpty() == false) {
                Node node = queue.poll();

                if (groups.containsKey(node) == false) {
                    /* Create a new entry */
                    group.add(node);
                    groups.put(node, group);

                    /* Add children to queue for BFS */
                    for (Node child : node.children)
                        queue.offer(child);

                    /* Mark the node as processed */
                    visited.add(node);

                } else {

                    /* Update an old entry iff. the old entry is another cluster */
                    Group oldGroup = groups.get(node);
                    if (oldGroup != group) {
                        oldGroup.union(group);
                        oldGroup.root = group.root;
                    }
                }
            }
        }

        /* Collect the equivalent roots of all the groups */
        HashSet<Node> roots2 = new HashSet<>();
        for (Group group : groups.values())
            if (roots2.contains(group.root) == false)
                roots2.add(group.root);
        _results.addAll(roots2);

        return _results;
    }
}
