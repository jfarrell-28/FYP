package Main;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reviews {
    private String fileName;
    private Map<String, Map<String, Integer>> userItemMatrix;

    public Reviews(String fileName) {
        this.fileName = fileName;
        this.userItemMatrix = new HashMap<>();
    }

    public String getFileName() {
        return fileName;
    }

    public void readCSV() {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            String[] nextLine;

            try {
                while ((nextLine = reader.readNext()) != null) {
                    // Assuming the CSV structure is: member_id, hotel_id, rating
                    String memberId = nextLine[0];
                    String hotelId = nextLine[1];
                    int rating = Integer.parseInt(nextLine[2]);

                    // Populate the user-item matrix
                    userItemMatrix.computeIfAbsent(memberId, k -> new HashMap<>()).put(hotelId, rating);
                }
            } catch (CsvValidationException e) {
                e.printStackTrace(); // Handle the CsvValidationException
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, Double>> calculateSimilarityMatrix() {
        Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();

        // Loop through users
        for (Map.Entry<String, Map<String, Integer>> entry1 : userItemMatrix.entrySet()) {
            String user1 = entry1.getKey();
            Map<String, Integer> ratings1 = entry1.getValue();
            Map<String, Double> userSimilarity = new HashMap<>();

            // Loop through other users
            for (Map.Entry<String, Map<String, Integer>> entry2 : userItemMatrix.entrySet()) {
                String user2 = entry2.getKey();
                Map<String, Integer> ratings2 = entry2.getValue();

                // Calculate Pearson correlation coefficient
                double similarity = calculatePearsonCorrelation(ratings1, ratings2);

                // Store the similarity value in the matrix
                userSimilarity.put(user2, similarity);
            }

            // Add the user's similarity matrix to the overall similarity matrix
            similarityMatrix.put(user1, userSimilarity);
        }

        return similarityMatrix;
    }

    private double calculatePearsonCorrelation(Map<String, Integer> ratings1, Map<String, Integer> ratings2) {
        // Step 1: Find common items
        Map<String, Integer> commonItems = new HashMap<>(ratings1);
        commonItems.keySet().retainAll(ratings2.keySet());

        int commonSize = commonItems.size();

        if (commonSize == 0) {
            return 0.0; // No common items, correlation is undefined
        }

        // Step 2: Calculate the sum of products and sum of squares
        double sumProduct = 0.0;
        double sumSquaredRatings1 = 0.0;
        double sumSquaredRatings2 = 0.0;

        for (String item : commonItems.keySet()) {
            int rating1 = ratings1.get(item);
            int rating2 = ratings2.get(item);

            sumProduct += rating1 * rating2;
            sumSquaredRatings1 += Math.pow(rating1, 2);
            sumSquaredRatings2 += Math.pow(rating2, 2);
        }

        // Step 3: Calculate the Pearson correlation coefficient
        double numerator = sumProduct - (sumSquaredRatings1 * sumSquaredRatings2 / commonSize);
        double denominator = Math.sqrt((sumSquaredRatings1 - Math.pow(sumSquaredRatings1, 2) / commonSize)
                * (sumSquaredRatings2 - Math.pow(sumSquaredRatings2, 2) / commonSize));

        if (denominator == 0.0) {
            return 0.0; // Handle division by zero (correlation is undefined)
        }

        return numerator / denominator;
    }


    public static void main(String[] args) {
        String reviewFileName = "C:\\Users\\Jobby\\Desktop\\College\\Final Year Project\\TripAdvisorDataset\\review.csv";
        Reviews reviewsReader = new Reviews(reviewFileName);
        reviewsReader.readCSV();

        // Generate and print the similarity matrix
        Map<String, Map<String, Double>> similarityMatrix = reviewsReader.calculateSimilarityMatrix();
        printSimilarityMatrix(similarityMatrix);
    }

    private static void printSimilarityMatrix(Map<String, Map<String, Double>> similarityMatrix) {
        for (Map.Entry<String, Map<String, Double>> entry : similarityMatrix.entrySet()) {
            String user1 = entry.getKey();
            Map<String, Double> userSimilarities = entry.getValue();

            System.out.print("User: " + user1 + " - Similarities: ");
            for (Map.Entry<String, Double> similarityEntry : userSimilarities.entrySet()) {
                String user2 = similarityEntry.getKey();
                double similarity = similarityEntry.getValue();
                System.out.print("(" + user2 + ": " + similarity + ") ");
            }
            System.out.println();
        }
    }
}
