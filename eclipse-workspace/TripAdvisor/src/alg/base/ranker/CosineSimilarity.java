package alg.base.ranker;

public class CosineSimilarity {

    public static double calculate(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input arrays must have the same length");
        }

        double dotProduct = 0;
        double normX = 0, normY = 0;

        for (int i = 0; i < x.length; i++) {
            dotProduct += x[i] * y[i];
            normX += x[i] * x[i];
            normY += y[i] * y[i];
        }

        double denominator = Math.sqrt(normX) * Math.sqrt(normY);

        if (denominator == 0) {
            return 0; // To handle division by zero
        }

        return dotProduct / denominator;
    }

    public static void main(String[] args) {
        // Example usage
        double[] x = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] y = {2.0, 3.0, 4.0, 5.0, 6.0};
        double cosineSimilarity = calculate(x, y);
        System.out.println("Cosine Similarity: " + cosineSimilarity);
    }
}

