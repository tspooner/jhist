// Import statements
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

public class AdaptiveStorage extends HistogramStorage {

    private Tree<Bin> tree;
    private int total = 0;

    public AdaptiveStorage() {
        tree = new Tree<Bin>(new Bin());
    }

    public AdaptiveStorage(double min, double max) {
        tree = new Tree<Bin>(new Bin(0, min, max));
    }

    public void add(double value) {
        List<Tree<Bin>> fringe = tree.fringe();

        for (Tree<Bin> leaf : fringe) {
            Bin bin = leaf.getValue();
            if (bin.contains(value)) {
                total++;

                if (bin.count >= getSplitLimit()) {
                    List<Bin> newBins;

                    if (value > bin.getCentre())
                        newBins = bin.split(false);
                    else
                        newBins = bin.split(true);


                    if (newBins.get(0).contains(value)) newBins.get(0).increment();
                    else newBins.get(1).increment();

                    leaf.setLeft(new Tree<Bin>(newBins.get(0)));
                    leaf.setRight(new Tree<Bin>(newBins.get(1)));
                } else bin.increment();
            } else continue;
        }
    }

    public int getTotal() { return getTotal(tree); }

    private int getTotal(Tree<Bin> node) {
        int sum = 0;
        for (Tree<Bin> leaf : node.fringe())
            sum += leaf.getValue().count;

        return sum;
    }

    private int getSplitLimit() {
        if (total == 1) return 1;
        else return total / 10;
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
        for (Tree<Bin> node : tree.fringe()) {
            Bin bin = node.getValue();

			sb.append(
				"Bin @ " + dfMore.format(bin.getCentre()) + ": " + bin.getCount() +
				"\t[\u03c3 = " + df.format(error(bin.getCount())) + "]" +
				"\t(" + dfMore.format(bin.getCentre()) + ")" + sep
			); // U+03C3 is a sigma character
		}

        sb.append(sep + "Total Count:\t" + total);
        sb.append(sep + "Total Count:\t" + getTotal());

        return sb.toString();
    }
}
