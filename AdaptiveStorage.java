// Import statements
import java.text.DecimalFormat;

public class AdaptiveStorage extends HistogramStorage {

    private Tree<Bin> store;

    private int uncounted = 0;

    public AdaptiveStorage() {
        store = new Tree<Bin>(new Bin());
    }

    public AdaptiveStorage(double min, double max) {
        store = new Tree<Bin>(new Bin(0, min, max));
    }

    public void add(double value) {
        if (!store.getRoot().getData().contains(value)) {
            uncounted++;
        } else {
            for (Bin bin : store.getLeaves()) {
                if (bin.contains(value)) {
                    bin.increment();
                    break;
                }
            }
        }
    }

    // Export methods
    public String toCsv() { return ""; }
    public int[] toArray() { return new int[2]; }

    public String toPrettyString() {
        StringBuilder sb = new StringBuilder();

		String sep = System.getProperty("line.separator");
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfMore = new DecimalFormat("#0.0000");

        sb.append(sep);

		// Add all the leaf nodes
        for (Bin bin : store.getLeaves()) {
			sb.append(
				"Bin @ " + bin.getCentre() + ": " + bin.getCount() +
				"\t[\u03c3 = " + df.format(error(bin.getCount())) + "]" +
				"\t(" + dfMore.format(bin.getCentre()) + ")" + sep
			); // U+03C3 is a sigma character
		}

        return sb.toString();
    }
}
