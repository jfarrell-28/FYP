package utill;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Profile 
{
    private final Integer userId; // the user ID as a string
    private Map<Integer, Double> dataMap;
    private List<Integer> reviewDistribution; // Add a field to store the review distribution
    private int reviewNum; // Add a field to store the review number


    /**
     * Constructor - creates a new Profile object
     *
     * @param id the user ID as a string
     */
    public Profile(final Integer id) {
        this.userId = id;
        this.dataMap = new HashMap<>();
    }
    
    public Integer getId()
    {
    	return dataMap.size();
    }

    /**
     * @return the user ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @return the profile size
     */
    public int getSize() {
        return dataMap.size();
    }
    

    /**
     * @return true if the ID is in the profile
     */
    public boolean contains(final Integer id) {
        return dataMap.containsKey(id);
    }

    /**
     * @return the value for the ID (or null if ID is not in profile)
     */
    public Double getValue(final Integer id)
	{
		return dataMap.get(id);
	}

    /**
     * @return the mean value over all values in the profile
     */
    public double getMeanValue()
	{
		double total = 0;

		for(Double r: dataMap.values())
			total += r.doubleValue();

		return getSize() > 0 ? total / getSize() : 0;
	}

    /**
     * @return the norm of all values in the profile
     */
    public double getNorm()
	{
		double sumsq = 0;

		for(Double r: dataMap.values())
			sumsq += Math.pow(r.doubleValue(), 2);

		return Math.sqrt(sumsq);
	}

    /**
     * @return the set of IDs in the profile
     */
    public Set<Integer> getIds() {
        return dataMap.keySet();
    }
    
 // Method to add a rating to the profile
    public void addRating(Integer userId, double rating) {
    	dataMap.put(userId, rating);
    }

    // Method to get all ratings in the profile
    public Map<Integer, Double> getRatings() {
        return new HashMap<>(dataMap); // Return a copy to prevent modification of the internal map
    }

    /**
     * @param other the other profile
     * @return a set of IDs that two profiles have in common
     */
    public Set<Integer> getCommonIds(final Profile other) {
        Set<Integer> common = new HashSet<>();

        for (Integer id : getIds())
            if (other.contains(id))
                common.add(id);

        return common;
    }

    /**
     * @param id    the ID to be added to the profile
     * @param value the corresponding value
     */
    public void addValue(final Integer id, final Double value) {
        dataMap.put(id, value);
    }
    
    
    public List<Integer> getReviewDistribution()
    {
    	return reviewDistribution;
    }
    
    public void setReviewDistribution(List<Integer> reviewDistribution)
    {
    		this.reviewDistribution = reviewDistribution;
    }
    
    public int getReviewNum()
    {
    	return reviewNum;
    }
    
    public void setReviewNum(int reviewNum)
    {
    	this.reviewNum = reviewNum;
    }


    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        for (Map.Entry<Integer, Double> entry : dataMap.entrySet()) {
            Integer userId = entry.getKey();
            Double value = entry.getValue();
            buf.append(userId).append(" ").append(userId).append(" ").append(value).append("\n");
        }

        return buf.toString();
    }
}

