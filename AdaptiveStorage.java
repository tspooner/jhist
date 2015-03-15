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
        boolean counted = false;

        for (Tree<Bin> leaf : fringe) {
            if (counted) break;

            Bin bin = leaf.getValue();

            if (bin.contains(value)) {
                counted = true;

                total++;
                bin.increment();

                if (bin.count >= getSplitLimit() && bin.min != bin.max) {
                    List<Bin> newBins;

                    if (value > bin.getCentre())
                        newBins = bin.split();
                    else
                        newBins = bin.split();

                    leaf.setLeft(new Tree<Bin>(newBins.get(0)));
                    leaf.setRight(new Tree<Bin>(newBins.get(1)));

                    if (bin.count % 2 != 0) {
                        if (leaf.getLeft().getValue().contains(value))
                            leaf.getLeft().getValue().increment();
                        else
                            leaf.getRight().getValue().increment();
                    }
                }
            }
        }

        Bin toExtend;
        if (!counted) {
            if (fringe.get(0).getValue().min > value)
                toExtend = fringe.get(0).getValue();
            else
                toExtend = fringe.get((fringe.size()-1)).getValue();

            toExtend.extend(value);
            toExtend.increment();
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
    public String toCsv() {
        String csv = "";

        for (Tree<Bin> f : tree.fringe()) {
            Bin bin = f.getValue();
            csv += bin.getCentre() + "," + bin.getDensity(total) + "\n";
        }

        return csv;
    }

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
				"\t(" + dfMore.format(bin.max - bin.min) + ")" + sep
			); // U+03C3 is a sigma character
		}

        return sb.toString();
    }
}
