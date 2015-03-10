
public class Histogram {

    private Tree<Integer> tree;

    public Histogram() { reset(); }

    public void reset() { tree = null; }

    public void add(float value) {
        count++;

        if (null == root)
            root = new Tree<Integer>(value, null, null);
        else
            root.add(value);
    }

    public static void main(String[] args) {
        Tree<Integer> left = new Tree<>(new Integer(2), null, null);
        Tree<Integer> right = new Tree<>(new Integer(3), null, null);

        tree = new Tree<>(new Integer(1), null, null);

        System.out.println(tree.total());
    }
}
