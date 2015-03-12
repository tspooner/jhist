// Import statements
import java.text.DecimalFormat;

public class StaticStorage extends HistogramStorage {

    private int size; // Number of bins
    private int[] data; // Bin counts

    private double binLow; // Lower bound for bins
    private double binHigh; // Upper bound for bins
    private double binWidth; // Width of a single bin

    private int overflows; // Number of hits above upper bound
    private int underflows; // Number of hits below lower bound

    public StaticStorage(int size, double min, double max) {
        this.size = size;
        this.data = new int[size];

        this.binLow = min;
        this.binHigh = max;
        this.binWidth = binWidth(size, min, max);

        this.overflows = 0;
        this.underflows = 0;
    }

    public void add(double value) {
        if (value > binHigh || value < binLow) {
			if (value > binHigh) overflows++; // Number is too large
			if (value < binLow) underflows++; // Number is too small
		} else {
			data[(int) ((value - binLow) / binWidth)]++;
		}
    }

    public String toCsv() {
		String csv = "";

		for(int i = 0; i < data.length; i++)
			csv += data[i] + ",";

		return csv;
	}

    public int[] toArray() { return this.data; }

    public String toPrettyString() {
        StringBuilder sb = new StringBuilder();

		String sep = System.getProperty("line.separator");
		DecimalFormat df = new DecimalFormat("#0.00");
		DecimalFormat dfMore = new DecimalFormat("#0.0000");

        sb.append(sep);

		// Append data array
		double centre;
		for (int i = 0; i < size; i++) {
			centre = binLow + i*binWidth + binWidth/2;
			sb.append(
				"Bin " + (i+1) + ": " + data[i] +
				"\t[\u03c3 = " + df.format(error(data[i])) + "]" +
				"\t(" + dfMore.format(centre) + ")" + sep
			); // U+03C3 is a sigma character
		}

		// Append some other useful info
		sb.append(sep + "Bin width:\t" + binWidth);
		sb.append(sep + "Upper bound:\t" + binHigh);
		sb.append(sep + "Lower bound:\t" + binLow);

		sb.append(sep);

		sb.append(sep + "Underflows:\t" + underflows);
		sb.append(sep + "Overflows:\t" + overflows);

		return sb.toString();
	}
}
