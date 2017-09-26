package LeastPointsTraversing;

import java.util.ArrayList;

/**
 * Created by naco_siren on 9/25/17.
 */
public class Node {
    public int id;
    public ArrayList<Node> children;

    public Node(int id) {
        this.id = id;
        this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[" + id + "] w/ " + children.size();
    }
}
