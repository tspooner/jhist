
public class Tree<T> {
    private T count;

    Tree<T> left;
    Tree<T> right;

    public Tree(T newCount, Tree<T> newLeft, Tree<T> newRight) {
        count = newCount;
        left = newLeft;
        right = newRight;
    }

    public int total() {
        return count + left.total() + right.total();
    }
}
