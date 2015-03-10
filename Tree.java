
public class Tree {
    private int count;

    private float lower;
    private float upper;

    private Tree left;
    private Tree right;

    public Tree() { reset(); }

    public Tree(int count, float upper, float lower) {
        reset();

        this.count = count;
        this.upper = upper;
        this.lower = lower;
    }

    public void reset() {
        this.count = 0;
        this.lower = -Float.MAX_VALUE;
        this.upper = Float.MAX_VALUE;

        this.left = this.right = null;
    }

    public void add(float value) {
        if (value >= this.lower && value <= this.upper) {
            this.count++;

            if (this.count > 2 && this.lower != this.upper) {
                float splitAt = (this.lower + this.upper) / 2;
                int splitCount = this.count / 2;

                this.left = new Tree(splitCount, this.lower, splitAt);
                this.right = new Tree(splitCount, splitAt, this.upper);
            }
        }
    }

    public int total() {
        int sum = count;

        if (null != left) sum += left.total();
        if (null != right) sum += right.total();

        return sum;
    }
}
