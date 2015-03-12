import java.util.Random;

public class Histogram {

    private HistogramStorage store;

    public Histogram(int size, double min, double max) {
        this.store = new StaticStorage(size, min, max);
    }

    public void add(double value) { store.add(value); }

    public void dump() {
        System.out.println(store.toPrettyString());
    }

    public static void main(String[] args) {
        Histogram me = new Histogram(20, 0, 1);

        Random rng = new Random();

        for (int i = 0; i < 10000; i++)
            me.add(rng.nextDouble());

        me.dump();
    }
}
