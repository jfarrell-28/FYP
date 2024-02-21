package alg.base.evaluator;

import java.util.Random;

public class ABTesting {

    private static final int NUM_USERS = 1000;
    private static final int NUM_ITEMS = 500;
    private static final int EXPERIMENT_DURATION_DAYS = 14;

    public static void main(String[] args) {
        // Simulate A/B test with two versions of collaborative filtering algorithm
        CollaborativeFilteringAlgorithm versionA = new CollaborativeFilteringAlgorithm("Version A");
        CollaborativeFilteringAlgorithm versionB = new CollaborativeFilteringAlgorithm("Version B");

        runABTest(versionA, versionB);
    }

    private static void runABTest(CollaborativeFilteringAlgorithm versionA, CollaborativeFilteringAlgorithm versionB) {
        System.out.println("A/B Testing Collaborative Filtering Algorithms");

        // Simulate user interactions over the experiment duration
        simulateUserInteractions(versionA, EXPERIMENT_DURATION_DAYS);
        simulateUserInteractions(versionB, EXPERIMENT_DURATION_DAYS);

        // Collect and analyze results
        double averageRatingA = versionA.calculateAverageRating();
        double averageRatingB = versionB.calculateAverageRating();

        System.out.println("Results after " + EXPERIMENT_DURATION_DAYS + " days:");
        System.out.println("Average Rating for " + versionA.getName() + ": " + averageRatingA);
        System.out.println("Average Rating for " + versionB.getName() + ": " + averageRatingB);

        // Compare the performance of the two versions
        if (averageRatingA > averageRatingB) {
            System.out.println(versionA.getName() + " performs better.");
        } else if (averageRatingA < averageRatingB) {
            System.out.println(versionB.getName() + " performs better.");
        } else {
            System.out.println("Both versions perform equally.");
        }
    }

    private static void simulateUserInteractions(CollaborativeFilteringAlgorithm algorithm, int durationDays) {
        Random random = new Random();

        for (int day = 1; day <= durationDays; day++) {
            for (int userId = 1; userId <= NUM_USERS; userId++) {
                int itemId = random.nextInt(NUM_ITEMS) + 1;
                int rating = random.nextInt(5) + 1; // Simulating a rating between 1 and 5
                algorithm.recordUserInteraction(userId, itemId, rating);
            }
        }
    }
}

class CollaborativeFilteringAlgorithm {
    private String name;
    private int[][] userItemMatrix;

    public CollaborativeFilteringAlgorithm(String name) {
        this.name = name;
        this.userItemMatrix = new int[NUM_USERS][NUM_ITEMS];
    }

    public String getName() {
        return name;
    }

    public void recordUserInteraction(int userId, int itemId, int rating) {
        if (userId > 0 && userId <= NUM_USERS && itemId > 0 && itemId <= NUM_ITEMS) {
            userItemMatrix[userId - 1][itemId - 1] = rating;
        }
    }

    public double calculateAverageRating() {
        double totalRating = 0;
        int totalInteractions = 0;

        for (int i = 0; i < NUM_USERS; i++) {
            for (int j = 0; j < NUM_ITEMS; j++) {
                if (userItemMatrix[i][j] > 0) {
                    totalRating += userItemMatrix[i][j];
                    totalInteractions++;
                }
            }
        }

        if (totalInteractions == 0) {
            return 0; // To handle division by zero
        }

        return totalRating / totalInteractions;
    }
}
