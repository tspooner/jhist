// Import statements
import java.util.List;
import java.util.ArrayList;

public class Tree<T> {
    private Node<T> root;
    private List<Node<T>> leaves;

    public Tree(T rootData) {
        root = new Node<T>(rootData, null);

        leaves = new ArrayList<Node<T>>();
        linkLeaves();
    }

    public Node<T> getRoot() { return root; }

    public List<T> getLeaves() {
        int size = leaves.size();
        List<T> data = new ArrayList<T>();

        for (Node<T> node : leaves)
            data.add(node.getData());

        return data;
    }

    private List<Node<T>> getLeaves(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<Node<T>>();

        if (!node.hasChildren()) {
            nodes.add(root);
            return nodes;
        }

        for (Node<T> child : node.getChildren()) {
            if (!child.hasChildren()) nodes.add(child);
            else getLeaves(child);
        }

        return nodes;
    }

    private void linkLeaves() {
        leaves.clear();
        leaves = getLeaves(root);
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;

        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
            this.children = new ArrayList<Node<T>>();
        }

        public Node(T data, Node<T> parent, List<Node<T>> children) {
            this.data = data;
            this.parent = parent;
            this.children = children;
        }

        public T getData() { return data; }

        public List<Node<T>> getChildren() { return children; }
        public boolean hasChildren() { return (children.size() > 0); }
    }
}
