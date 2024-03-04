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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utill.Profile;
import utill.UserItemPair;
import utill.Item;

public class DatasetReader {
    private Map<Integer, Profile> userProfileMap;
    private Map<Integer, Profile> itemProfileMap;
    private Map<UserItemPair, Double> testData;
    private Map<UserItemPair, Double> trainingData;
    private Map<Integer, Item> itemMap;
    private Set<String> skippedMemberIds = new HashSet<>();

    public DatasetReader(final String itemFile, final String trainFile, final String testFile) 
    {
    	// Initialize data structures
        userProfileMap = new HashMap<>();
        itemProfileMap = new HashMap<>();
        testData = new HashMap<>();
        trainingData = new HashMap<>();
        itemMap = new HashMap<>();
        
    	loadItems(itemFile);
    	loadProfiles(trainFile);
    	loadTestData(testFile);
    }

	
	  public void loadDataset(String directory) 
	  { 
		 String hotelFilePath = directory + File.separator + "hotelsBase.csv"; 
		 String reviewFilePath = directory + File.separator + "reviewTraining.csv";
		 String test = directory + File.separator + "reviewTesting.csv";
		 loadProfiles(test); 
		 loadItems(hotelFilePath);
		 loadTestData(reviewFilePath); 
	  }
	  
	  public void loadDataset()
	  {
		  
	  }
	 

	  public void loadProfiles(final String filename) {
		    userProfileMap = new HashMap<>(); // Initialize user profile map
		    itemProfileMap = new HashMap<>(); // Initialize item profile map

		    try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
		        String line;
		        int lineNumber = 0;
		        boolean skipHeader = true; // Flag to skip the header line
		        
		        while ((line = br.readLine()) != null) {
		            lineNumber++;

		            if (skipHeader) {
		                skipHeader = false;
		                continue; // Skip the header line
		            }

		            StringTokenizer st = new StringTokenizer(line, ", \t\n\r\f");

		            if (st.countTokens() != 3) {
		                System.out.println("Error reading from file \"" + filename + "\" at line " + lineNumber + ". Incorrect number of tokens. Line content: " + line);
		                continue;
		            }

		            try {
		                Integer userId = Integer.valueOf(st.nextToken().trim()); // Assuming member_id is the first token
		                Integer hotelId = Integer.valueOf(st.nextToken().trim()); // Assuming name is the second token
		                Double rating = Double.valueOf(st.nextToken().trim()); // Assuming review_num is the third token

		                // Create a new Profile object with the memberId
		                Profile userProfile = userProfileMap.containsKey(userId) ? userProfileMap.get(userId) : new Profile(userId);
		                userProfile.addValue(hotelId, rating);
		                userProfileMap.put(userId, userProfile);
		                
		                Profile itemProfile = itemProfileMap.containsKey(hotelId) ? itemProfileMap.get(hotelId) : new Profile(hotelId);
		                itemProfile.addValue(userId, rating);
		                itemProfileMap.put(hotelId, itemProfile);

		            } catch (NumberFormatException e) {
		                System.out.println("Error parsing data at line " + lineNumber + ": " + e.getMessage() + ". Line content: " + line);
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.exit(0);
		    }
		}




	  public void loadTestData(final String filename) {
		    final double splitRatio = 0.2; // 20% test, 80% training
		    
		    // Initialize testData map
		    if (testData == null) {
		        testData = new HashMap<>();
		    }
		    
		    if (trainingData == null)
		    {
		    	trainingData = new HashMap<>();
		    }

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
		                Integer userId = Integer.valueOf(st.nextToken().trim());
		                Integer itemId = Integer.valueOf(st.nextToken().trim());
		                Double rating = Double.parseDouble(st.nextToken().trim());

		                // Construct the user-item pair
		                UserItemPair userItemPair = new UserItemPair(userId, itemId);

		                // Randomly assign to training or test set based on split ratio
		                if (Math.random() < splitRatio) {
		                    // Add to test data
		                    testData.put(userItemPair, rating);
		                } else {
		                    // Add to training data
		                    trainingData.put(userItemPair, rating);
		                }
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

		    try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
		        // Skip the header line
		        br.readLine();

		        String line;
		        while ((line = br.readLine()) != null) {
		            String[] tokens = line.split(",");

		            // Check if the line has at least the minimum number of tokens required
		            if (tokens.length < 2) {
		                System.out.println("Error reading from file \"" + filename + "\": Insufficient number of tokens. Line content: " + line);
		                System.exit(1);
		            }

		            try {
		                Integer id = Integer.valueOf(tokens[0]);
		                String name = tokens[1];
		                Item item = new Item(id, name);
		                itemMap.put(id, item);
		            } catch (NumberFormatException e) {
		                System.out.println("Error parsing data: " + e.getMessage());
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.exit(0);
		    }
		}




    public Map<Integer, Profile> getUserProfiles() {
        return userProfileMap;
    }

    public Map<Integer, Profile> getItemProfiles() {
        return itemProfileMap;
    }

    public Map<UserItemPair, Double> getTestData() {
        return testData;
    }

    public Map<Integer, Item> getItems() {
        return itemMap;
    }
    
    public Item getItem(Integer id)
    {
    	return itemMap.get(id);
    }

    public Set<String> getSkippedMemberIds() {
        return skippedMemberIds;
    }
    
    public Map<UserItemPair, Double> getTrainingData() {
        return trainingData;
    }


	
    public static void main(String[] args) {
        // Create an instance of DatasetReader
        DatasetReader datasetReader = new DatasetReader("hotelsBase.csv", "reviewTraining.csv", "Reviews.csv");
        // Load the dataset from the specified directory
        datasetReader.loadDataset("C:\\Users\\Jobby\\Desktop\\College\\Final Year Project\\TripAdvisorDataset\\");

        Map<Integer, Profile> userProfiles = datasetReader.getUserProfiles();
        Map<Integer, Profile> itemProfiles = datasetReader.getItemProfiles();
        Map<UserItemPair, Double> testData = datasetReader.getTestData();
        Map<UserItemPair, Double> trainingData = datasetReader.getTrainingData();
        Map<Integer, Item> items = datasetReader.getItems();

        System.out.println("User profiles loaded: " + userProfiles.size());
        System.out.println("Item profiles loaded: " + itemProfiles.size());
        System.out.println("Test data loaded: " + testData.size());
        System.out.println("Training data Loaded: " + trainingData.size());
        System.out.println("Items loaded: " + items.size());
    }
	 
}

