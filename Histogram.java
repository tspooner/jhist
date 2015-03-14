import java.util.Random;

public class Histogram {

    private HistogramStorage store;
    private HistogramStorage store2;

    public Histogram(double min, double max) {
        this.store = new AdaptiveStorage(min, max);
        this.store2 = new StaticStorage(25, min, max);
    }

    public void add(double value) { store.add(value); store2.add(value); }

    public void dump() {
        System.out.println(store.toPrettyString());
        System.out.println("\n");
        System.out.println(store2.toPrettyString());
    }

    public static void main(String[] args) {
        Histogram me = new Histogram(-10, 10);

        Random rng = new Random();

        for (int i = 0; i < 100000; i++)
            me.add(rng.nextGaussian());

        me.dump();
    }
}
