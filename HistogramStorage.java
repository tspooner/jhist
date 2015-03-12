
abstract class HistogramStorage {

    public abstract void add(double value);

    public void add(double[] values) {
        for (double value : values)
            this.add(value);
    }

    public abstract String toCsv();
    public abstract int[] toArray();
    public abstract String toPrettyString();

    protected static double binWidth(int size,
								     double min,
								     double max) {
        return (max - min) / ((double) size);
	}

    protected static double error(int count) {
        return Math.sqrt(count);
    }
}
