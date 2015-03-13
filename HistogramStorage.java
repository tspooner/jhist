
abstract class HistogramStorage {

    // Base methods
    public abstract void add(double value);

    public void add(double[] values) {
        for (double value : values)
            this.add(value);
    }

    // Export methods
    public abstract String toCsv();
    public abstract int[] toArray();
    public abstract String toPrettyString();

    // Helper methods
    protected static double error(int count) {
        return Math.sqrt(count);
    }

    static class Bin {
        public int count;
        public double min;
        public double max;

        public Bin() {
            this.count = 0;
            this.min = -Double.MAX_VALUE;
            this.max = Double.MAX_VALUE;
        }

        public Bin(int count, double min, double max) {
            this.count = count;
            this.min = min;
            this.max = max;
        }

        public void increment() { this.count++; }
        public void decrement() { this.count--; }

        public boolean contains(double value) {
            return (value >= min && value <= max);
        }

        public int getCount() { return this.count; }
        public double getCentre() { return (max + min) / 2; }
    }
}
