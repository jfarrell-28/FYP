package Main;

import java.util.ArrayList;
import java.util.List;

public class RecommendationSystemApp {
    private List<String> users;  // Assume users are represented by names

    public RecommendationSystemApp() {
        // Initialize some sample users
        users = new ArrayList<>();
        users.add("User1");
        users.add("User2");
        users.add("User3");
        // Add more users as needed
    }

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

        // Access RecommendationSystemApp instance to get some useful value
        RecommendationSystemApp instance = userItemMatrix.recommendationSystemApp();
        int totalUsers = instance.getSomeValue();
        System.out.println("Total number of users: " + totalUsers);
    }

    // Updated method to return the total number of users
    public int getSomeValue() {
        return users.size();
    }
    
    
}

