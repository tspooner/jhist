// Import statements
import java.util.List;
import java.util.ArrayList;

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

    public class Bin {
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

        public List<Bin> split() { return split(true); }
        public List<Bin> split(boolean toLeft) {
            List<Bin> split = new ArrayList<Bin>();

            double splitAt = (this.min + this.max) / 2;
            boolean isOdd = count % 2 == 0;

            int splitCount = (count / 2);

            if (toLeft) {
                split.add(new Bin(splitCount, min, splitAt));
                split.add(new Bin((splitCount+(isOdd?1:0)), splitAt, max));
            } else {
                split.add(new Bin((splitCount+(isOdd?1:0)), min, splitAt));
                split.add(new Bin(splitCount, splitAt, max));
            }

            return split;
        }
    }
}
