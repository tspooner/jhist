import java.util.Random;

public class Histogram {

    private HistogramStorage store;

    public Histogram(double min, double max) {
        this.store = new AdaptiveStorage(min, max);
    }

    public void add(double value) { store.add(value); }

    public void dump() {
        System.out.println(store.toPrettyString());
    }

    public static void main(String[] args) {
        Histogram me = new Histogram(-10, 10);

        Random rng = new Random();

        for (int i = 0; i < 50; i++)
            me.add(rng.nextGaussian());

        me.dump();
    }
}
