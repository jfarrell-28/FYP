package utill;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Profile {
    private final String userId; // the user ID as a string
    private Map<String, Double> dataMapString; // stores ratings or similarities (Strings)
    private Map<Integer, Double> dataMapInteger; // sore ratings or similarities (Integers)
    private Map<Integer, Item> dataMap;

    /**
     * Constructor - creates a new Profile object
     *
     * @param id the user ID as a string
     */
    public Profile(final String id) {
        this.userId = id;
        this.dataMapString = new HashMap<>();
        this.dataMapInteger = new HashMap<>();
        this.dataMap = new HashMap<>();
    }

    /**
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return the profile size
     */
    public int getSize() {
        return dataMapString.size();
    }

    /**
     * @return true if the ID is in the profile
     */
    public boolean contains(final String id) {
        return dataMapString.containsKey(id);
    }

    /**
     * @return the value for the ID (or null if ID is not in profile)
     */
    public Double getValue(final Integer id) {
        return dataMapString.get(id);
    }

    /**
     * @return the mean value over all values in the profile
     */
    public double getMeanValue() {
        double total = 0;

        for (Double r : dataMapString.values())
            total += r.doubleValue();

        return getSize() > 0 ? total / getSize() : 0;
    }

    /**
     * @return the norm of all values in the profile
     */
    public double getNorm() {
        double sumsq = 0;

        for (Double r : dataMapString.values())
            sumsq += Math.pow(r.doubleValue(), 2);

        return Math.sqrt(sumsq);
    }

    /**
     * @return the set of IDs in the profile
     */
    public Set<String> getIds() {
        return dataMapString.keySet();
    }

    /**
     * @param other the other profile
     * @return a set of IDs that two profiles have in common
     */
    public Set<String> getCommonIds(final Profile other) {
        Set<String> common = new HashSet<>();

        for (String id : getIds())
            if (other.contains(id))
                common.add(id);

        return common;
    }

    /**
     * @param id    the ID to be added to the profile
     * @param value the corresponding value
     */
    public void addValueString(final String id, final Double value) {
        dataMapString.put(id, value);
    }
    
    public void addValueInteger(final Integer id, final Double value) {
        dataMapInteger.put(id, value);
    }
    
    public void printDataMap() {
        for (Map.Entry<Integer, Item> entry : dataMap.entrySet()) {
            Integer id = entry.getKey();
            Item item = entry.getValue();
            System.out.println(id + ": " + item);
        }
    }

    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        for (Map.Entry<Integer, Item> entry : dataMap.entrySet()) {
            Integer id = entry.getKey();
            Item item = entry.getValue();
            buf.append(userId + " " + id + " " + item + "\n");
        }

        return buf.toString();
    }
}

