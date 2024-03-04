package alg.base.ranker;

public class JaccardSimilarity {

    public static double calculate(int[] x, int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input arrays must have the same length");
        }

        int intersection = 0;
        int union = 0;

        for (int i = 0; i < x.length; i++) {
            if (x[i] == y[i]) {
                intersection++;
            }
            union++;
        }

        if (union == 0) {
            return 0; // To handle division by zero
        }

        return (double) intersection / union;
    }

    public static void main(String[] args) {
        // Example usage
        int[] a = {1, 1, 0, 1, 0};
        int[] b = {1, 1, 1, 0, 1};
        double jaccardSimilarity = calculate(a, b);
        System.out.println("Jaccard Similarity: " + jaccardSimilarity);
    }
}
