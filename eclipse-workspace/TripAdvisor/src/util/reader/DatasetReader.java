package util.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import utill.Profile;
import utill.UserItemMatrixBuilder;
import utill.UserItemPair;
import utill.Item;

public class DatasetReader {
    private Map<String, Profile> userProfileMap;
    private Map<String, Profile> itemProfileMap;
    private Map<UserItemPair, Double> testData;
    private Map<String, Item> itemMap;
    private Set<String> skippedMemberIds = new HashSet<>();

    public DatasetReader() {
    }

    public void loadDataset(String directory) {
        String memberFilePath = directory + File.separator + "members.csv";
        String hotelFilePath = directory + File.separator + "hotel.csv";
        String reviewFilePath = directory + File.separator + "reviews.csv";

        loadProfiles(memberFilePath);
        loadItems(hotelFilePath);
        loadTestData(reviewFilePath);
    }

    private void loadProfiles(final String filename) {
        userProfileMap = new HashMap<>();
        itemProfileMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                //System.out.println("Reading line " + lineNumber + ": " + line); // Debugging statement
                
                StringTokenizer st = new StringTokenizer(line, ", \t\n\r\f");

                if (filename.endsWith("members.csv")) {
                    String memberId = st.hasMoreTokens() ? st.nextToken() : "";
                    skippedMemberIds.add(memberId);
                    continue; 
                }

                if (st.countTokens() != 4) {
                    System.out.println("Error reading from file \"" + filename + "\" at line " + lineNumber + ". Incorrect number of tokens. Line content: " + line);
                    continue; 
                }

                try {
                    Integer userId = tryParseInt(st.nextToken());
                    Integer itemId = tryParseInt(st.nextToken());
                    Double rating = Double.valueOf(st.nextToken());

                    // Check if userId or itemId is null (parsing failed)
                    if (userId == null || itemId == null) {
                        System.out.println("Error parsing data at line " + lineNumber + ". Skipping line. Line content: " + line);
                        continue;
                    }

                    // Convert userId and itemId to String
                    String userIdStr = String.valueOf(userId);
                    String itemIdStr = String.valueOf(itemId);

                    // add data to user profile map
                    Profile up = userProfileMap.computeIfAbsent(userIdStr, k -> new Profile(userIdStr));
                    up.addValueInteger(itemId, rating);

                    // add data to item profile map
                    Profile ip = itemProfileMap.computeIfAbsent(itemIdStr, k -> new Profile(itemIdStr));
                    ip.addValueInteger(userId, rating);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing data at line " + lineNumber + ": " + e.getMessage() + ". Line content: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    private void loadTestData(final String filename) {
        testData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;

                StringTokenizer st = new StringTokenizer(line, ", \t\n\r\f");

                // Process review data
                if (st.countTokens() < 3) {
                    // Skip lines with incorrect number of tokens
                    continue;
                }

                try {
                    String userId = st.nextToken().trim();
                    String itemId = st.nextToken().trim();
                    Double rating = Double.parseDouble(st.nextToken().trim());

                    // Construct the user-item pair
                    UserItemPair userItemPair = new UserItemPair(userId, itemId);

                    // Add data to test data map
                    testData.put(userItemPair, rating);
                } catch (NumberFormatException e) {
                    // Skip lines with invalid rating format
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    private void loadItems(final String filename) {
        itemMap = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(new File(filename)))) {
            String[] line;
            int lineNumber = 0;
            while ((line = csvReader.readNext()) != null) {
                lineNumber++;
                String lineString = Arrays.toString(line);
                // System.out.println("Reading line " + lineNumber + ": " + lineString); // Debugging statement

                // Adjust the index based on the structure of your CSV file
                if (line.length < 2) {
                    System.out.println("Error reading from file \"" + filename + "\" at line " + lineNumber + ". Incorrect number of tokens.");
                    continue; // Skip this line and move to the next one
                }

                try {
                    String id = String.valueOf(line[0]);
                    String name = line[1];
                    Item item = new Item(id, name);
                    itemMap.put(id, item);
                } catch (Exception e) {
                    System.out.println("Error parsing data at line " + lineNumber + ": " + e.getMessage() + ". Line content: " + lineString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }


    private Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }

    private Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null; // Parsing failed
        }
    }

    public Map<String, Profile> getUserProfiles() {
        return userProfileMap;
    }

    public Map<String, Profile> getItemProfiles() {
        return itemProfileMap;
    }

    public Map<UserItemPair, Double> getTestData() {
        return testData;
    }

    public Map<String, Item> getItems() {
        return itemMap;
    }

    public Set<String> getSkippedMemberIds() {
        return skippedMemberIds;
    }
    
    public Map<String, Map<String, Double>> buildUserItemMatrix() {
        Map<String, Map<String, Double>> userItemMatrix = new HashMap<>();

        // Iterate over user profiles
        for (Map.Entry<String, Profile> entry : userProfileMap.entrySet()) {
            String userId = entry.getKey();
            Profile userProfile = entry.getValue();
            Map<Integer, Double> ratings = userProfile.getRatingsAsMapInteger(); // Use the new method

            // Iterate over ratings in the user profile
            for (Map.Entry<Integer, Double> ratingEntry : ratings.entrySet()) {
                Integer itemId = ratingEntry.getKey();
                Double rating = ratingEntry.getValue();
                String itemIdStr = String.valueOf(itemId);

                // Check if the item exists in the item map
                if (itemMap.containsKey(itemIdStr)) {
                    // Add the rating to the user-item matrix
                    userItemMatrix.computeIfAbsent(userId, k -> new HashMap<>()).put(itemIdStr, rating);
                }
            }
        }

        return userItemMatrix;
    }


    public void printUserProfiles() {
        for (Map.Entry<String, Profile> entry : userProfileMap.entrySet()) {
            System.out.println("User ID: " + entry.getKey());
            System.out.println("User Profile: ");
            entry.getValue().printDataMap();  // Add this
            System.out.println("----------------------------------------");
        }
    }

    public static void main(String[] args) {
        DatasetReader datasetReader = new DatasetReader();
        datasetReader.loadDataset("C:\\Users\\Jobby\\Desktop\\College\\Final Year Project\\TripAdvisorDataset\\");

        // Check if all datasets are loaded successfully
        Map<String, Profile> userProfiles = datasetReader.getUserProfiles();
        Map<String, Profile> itemProfiles = datasetReader.getItemProfiles();
        Map<UserItemPair, Double> testData = datasetReader.getTestData();
        Map<String, Item> items = datasetReader.getItems();

        if (userProfiles != null && itemProfiles != null && testData != null && items != null) {
            // Print summary information
            System.out.println("User profiles loaded: " + userProfiles.size());
            System.out.println("Item profiles loaded: " + itemProfiles.size());
            System.out.println("Test data loaded: " + testData.size());
            System.out.println("Items loaded: " + items.size());

            // Build the user-item matrix
            UserItemMatrixBuilder matrixBuilder = new UserItemMatrixBuilder(userProfiles, items);
            Map<String, Map<String, Double>> userItemMatrix = matrixBuilder.getUserItemMatrix();

            // Print user-item matrix
            if (userItemMatrix != null) {
                System.out.println("User-Item Matrix:");
                for (Map.Entry<String, Map<String, Double>> entry : userItemMatrix.entrySet()) {
                    String userId = entry.getKey();
                    Map<String, Double> itemRatings = entry.getValue();
                    System.out.println("User ID: " + userId);
                    for (Map.Entry<String, Double> ratingEntry : itemRatings.entrySet()) {
                        String itemId = ratingEntry.getKey();
                        Double rating = ratingEntry.getValue();
                        System.out.println("Item ID: " + itemId + ", Rating: " + rating);
                    }
                    System.out.println("----------------------------------------");
                }
            } else {
                System.out.println("User-item matrix is null. Check the matrix building process.");
            }
        } else {
            System.out.println("One or more datasets are null. Check the data loading process.");
        }
    }

}

