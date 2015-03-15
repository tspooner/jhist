// Import statements
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Histogram {
    private HistogramStorage store;

    public Histogram(double min, double max, int size) {
        if (size > 0)
            this.store = new StaticStorage(size, min, max);
        else
            this.store = new AdaptiveStorage(min, max);
    }

    public Histogram(double min, double max) { this(min, max, 0); }
    public Histogram(int size) { this(-Double.MAX_VALUE, Double.MAX_VALUE, size); }
    public Histogram() { this(0); }

    public void reset() { store.reset(); }

    public void add(double value) { store.add(value); }

    public String toCsv() { return store.toCsv(); }
    public int[] toArray() { return store.toArray(); }
    public String toPrettyString() { return store.toPrettyString(); }

    public boolean writeToDisk(String filepath) throws IOException {
		FileWriter file;
		PrintWriter toFile = null;

		try {
			file = new FileWriter(filepath); // File stream
			toFile = new PrintWriter(file); // File writer
            toFile.print(store.toCsv());
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
			return false;
		} finally {
			if (toFile != null)
				toFile.close();

			return true;
		}
	}


    public static void main(String[] args) throws IOException {
        Histogram me = new Histogram(-10, 10);
        Random rng = new Random();

        for (int i = 0; i < 10000; i++)
            me.add(rng.nextGaussian());

        me.writeToDisk("./tmp.csv");
    }
}
