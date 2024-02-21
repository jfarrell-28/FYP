package alg.base.ranker;

public class WeightedEvaluation {

    // Weighted Pearson Correlation Similarity
    public static double weightedPearsonSimilarity(double[] x, double[] y, double[] weights) {
        if (x.length != y.length || x.length != weights.length) {
            throw new IllegalArgumentException("Input arrays and weights must have the same length");
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumXSquare = 0, sumYSquare = 0, sumWeights = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            sumX += x[i] * weights[i];
            sumY += y[i] * weights[i];
            sumXY += x[i] * y[i] * weights[i];
            sumXSquare += x[i] * x[i] * weights[i];
            sumYSquare += y[i] * y[i] * weights[i];
            sumWeights += weights[i];
        }

        double numerator = sumWeights * sumXY - sumX * sumY;
        double denominator = Math.sqrt((sumWeights * sumXSquare - sumX * sumX) * (sumWeights * sumYSquare - sumY * sumY));

        if (denominator == 0) {
            return 0; // To handle division by zero
        }

        return numerator / denominator;
    }

    // Weighted Cosine Similarity
    public static double weightedCosineSimilarity(double[] x, double[] y, double[] weights) {
        if (x.length != y.length || x.length != weights.length) {
            throw new IllegalArgumentException("Input arrays and weights must have the same length");
        }

        double dotProduct = 0;
        double normX = 0, normY = 0, normWeights = 0;

        for (int i = 0; i < x.length; i++) {
            dotProduct += x[i] * y[i] * weights[i];
            normX += x[i] * x[i] * weights[i];
            normY += y[i] * y[i] * weights[i];
            normWeights += weights[i];
        }

        double denominator = Math.sqrt(normX * normY);

        if (denominator == 0) {
            return 0; // To handle division by zero
        }

        return dotProduct / denominator;
    }

    // Weighted Jaccard Similarity
    public static double weightedJaccardSimilarity(int[] x, int[] y, double[] weights) {
        if (x.length != y.length || x.length != weights.length) {
            throw new IllegalArgumentException("Input arrays and weights must have the same length");
        }

        double intersection = 0;
        double union = 0;

        for (int i = 0; i < x.length; i++) {
            intersection += Math.min(x[i] * weights[i], y[i] * weights[i]);
            union += Math.max(x[i] * weights[i], y[i] * weights[i]);
        }

        if (union == 0) {
            return 0; // To handle division by zero
        }

        return intersection / union;
    }

    public static void main(String[] args) {
        // Example usage
        double[] x = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] y = {2.0, 3.0, 4.0, 5.0, 6.0};
        double[] weights = {0.5, 1.0, 0.5, 1.0, 0.5};

        System.out.println("Weighted Pearson Similarity: " + weightedPearsonSimilarity(x, y, weights));

        System.out.println("Weighted Cosine Similarity: " + weightedCosineSimilarity(x, y, weights));

        int[] a = {1, 1, 0, 1, 0};
        int[] b = {1, 1, 1, 0, 1};
        double[] jaccardWeights = {0.5, 1.0, 0.5, 1.0, 0.5};
        System.out.println("Weighted Jaccard Similarity: " + weightedJaccardSimilarity(a, b, jaccardWeights));
    }
}
