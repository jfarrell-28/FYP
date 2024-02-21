package alg.base.ranker;

public class PearsonSimilarity {

    public static double calculate(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input arrays must have the same length");
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumXSquare = 0, sumYSquare = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumXSquare += x[i] * x[i];
            sumYSquare += y[i] * y[i];
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumXSquare - sumX * sumX) * (n * sumYSquare - sumY * sumY));

        if (denominator == 0) {
            return 0; // To handle division by zero
        }

        return numerator / denominator;
    }

    public static void main(String[] args) {
        // Example usage
        double[] x = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] y = {2.0, 3.0, 4.0, 5.0, 6.0};
        double pearsonSimilarity = calculate(x, y);
        System.out.println("Pearson Similarity: " + pearsonSimilarity);
    }
}
