
public class AdaptiveStorage extends HistogramStorage {
    private int count;
    private double lower;
    private double upper;

    private AdaptiveStorage root;
    private AdaptiveStorage left;
    private AdaptiveStorage right;

    public AdaptiveStorage() { reset(); }

    public AdaptiveStorage(AdaptiveStorage root, int count, double lower, double upper) {
        reset();

        this.root = root;

        this.count = count;
        this.upper = upper;
        this.lower = lower;
    }

    public void reset() {
        this.root = this;

        this.count = 0;
        this.lower = -Double.MAX_VALUE;
        this.upper = Double.MAX_VALUE;

        this.left = this.right = null;
    }

    public void add(double value) {
        if (value >= this.lower && value <= this.upper) {
            if (null != this.left && null != this.right) {
                this.left.add(value);
                this.right.add(value);
            } else if (this.count > this.root.getSplitLimit() && this.lower != this.upper) {
                double splitAt = (this.lower + this.upper) / 2;
                int splitCount = this.count++ / 2;

                this.left = new AdaptiveStorage(this.root, splitCount, this.lower, splitAt);
                this.right = new AdaptiveStorage(this.root, splitCount, splitAt, this.upper);

                this.count = 0;
            } else { this.count++; }
        }
    }

    public int getTotal() {
        int sum = count;

        if (null != left) sum += left.getTotal();
        if (null != right) sum += right.getTotal();

        return sum;
    }

    private int getSplitLimit() {
        int limit = this.getTotal() / 10;

        if (0 == limit) limit = 1;

        return limit;
    }

    public String toCsv() {
		String csv = "";

		return csv;
	}

    public int[] toArray() { return new int[2]; }

    public String toPrettyString() { return ""; }
}
