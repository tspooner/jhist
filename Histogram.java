
public class Histogram {

    private Tree tree;

    public Histogram() { reset(); }

    public void reset() { tree = null; }

    public void add(float value) {
        if (null == tree)
            tree = new Tree();

        tree.add(value);
    }

    public int total() { return this.tree.total(); }

    public static void main(String[] args) {
        Histogram me = new Histogram();

        me.add(0f);
        me.add(2f);
        me.add(3f);
        me.add(4f);

        System.out.println(me.total());
    }
}
