package Main;

import java.util.HashMap;
import java.util.Map;

public class UIMatrix {
    private int[][] matrix;  
    private Map<Integer, Integer> userIndexMap;  
    private Map<Integer, Integer> hotelIndexMap;
    private RecommendationSystemApp recommendationSystemApp;
    

    public UIMatrix(int numUsers, int numHotels) {
        matrix = new int[numUsers][numHotels];
        userIndexMap = new HashMap<>();
        hotelIndexMap = new HashMap<>();
        recommendationSystemApp = new RecommendationSystemApp();

    }

 // Method to add a rating to the matrix
    public void addRating(int userId, int hotelId, int rating) {
        if (!userIndexMap.containsKey(userId)) {
            userIndexMap.put(userId, userIndexMap.size());
        }
        if (!hotelIndexMap.containsKey(hotelId)) {
            hotelIndexMap.put(hotelId, hotelIndexMap.size());
        }

        int userIndex = userIndexMap.get(userId);
        int hotelIndex = hotelIndexMap.get(hotelId);

        matrix[userIndex][hotelIndex] = rating;
        
    }

    // Method to get a rating from the matrix
    public int getRating(int userId, int hotelId) {
        if (userIndexMap.containsKey(userId) && hotelIndexMap.containsKey(hotelId)) {
            int userIndex = userIndexMap.get(userId);
            int hotelIndex = hotelIndexMap.get(hotelId);
            return matrix[userIndex][hotelIndex];
        }
        // Handle missing data, return a default value or throw an exception
        return 0;  // For simplicity, assuming missing ratings are represented as 0
    }

    // Add more methods as needed

    //Provide access to the RecommendationSystemApp instance
    public RecommendationSystemApp getRecommendationSystemApp() {
    return recommendationSystemApp;
    }

    public class RecommendationSystemApp {
    	public static void main(String[] args) {
    		// Initialize the UserItemMatrix with the number of users and hotels
    		UIMatrix userItemMatrix = new UIMatrix(10, 5);

    		// Add ratings to the matrix based on your reviews data
    		userItemMatrix.addRating(1, 1, 5);
    		userItemMatrix.addRating(2, 2, 4);
    		// ...

    		// Retrieve a rating from the matrix
    		int retrievedRating = userItemMatrix.getRating(1, 1);
    		System.out.println("Rating for User 1 and Hotel 1: " + retrievedRating);
    		}
    	}
}

