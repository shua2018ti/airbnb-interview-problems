package LeastPointsTraversing;

import java.util.HashSet;

/**
 * Created by naco_siren on 9/25/17.
 */
public class Group {
    public Node root;
    public HashSet<Node> members;

    public Group(Node root) {
        this.root = root;
        this.members = new HashSet<>();
    }

    public void add(Node node) {
        members.add(node);
    }

    public boolean contains(Node node) {
        return members.contains(node);
    }

    public void union(Group group) {
        members.addAll(group.members);
        group.members.addAll(members);
    }

    @Override
    public String toString() {
        return "[" + root.id + "] => " + members.size();
    }
}
