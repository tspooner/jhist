// Import statements
import java.util.List;
import java.util.ArrayList;

public class Tree<T> {
    private T data;
    private Tree<T> left, right;

    public Tree(T data, Tree<T> l, Tree<T> r) {
        this.data = data;
        left = l; right = r;
    }

    public Tree(T data) { this(data, null, null); }

    public T getValue() { return data; }
    public void setValue(T data) { this.data = data; }

    public boolean isInner() { return left != null || right != null; }
    public boolean isLeaf() { return left == null && right == null; }

    public boolean hasLeft() { return left != null; }
    public Tree<T> getLeft() { return left; }
    public void setLeft(Tree<T> l) { left = l; }

    public boolean hasRight() { return right != null; }
    public Tree<T> getRight() { return right; }
    public void setRight(Tree<T> r) { right = r; }

    public List<Tree<T>> fringe() {
        List<Tree<T>> f = new ArrayList<Tree<T>>();
        addToFringe(f);

        return f;
    }

    private void addToFringe(List<Tree<T>> f) {
        if (isLeaf())
            f.add(this);
        else {
            if (hasLeft()) left.addToFringe(f);
            if (hasRight()) right.addToFringe(f);
        }
    }
}
