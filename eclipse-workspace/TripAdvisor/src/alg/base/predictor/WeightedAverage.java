package alg.base.predictor;

/**
 * An class to compute the target user's predicted rating for the target item (user-based CF) using 
 * the weighted average technique.
 */
import java.util.Map;
import java.util.Set;

import alg.base.neighbourhood.Neighbourhood;
import alg.similarity.SimilarityMap;
import utill.Profile;



public class WeightedAverage implements Predictor 
{
	/**
	 * constructor - creates a new WeightedAveragePredictor object
	 */
	public WeightedAverage()
	{
	}

	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a map containing user-user similarities
	 */
	public Double getPrediction(final Integer userId, final Integer itemId, final Map<Integer,Profile> userProfileMap, final Map<Integer,Profile> itemProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		double below = 0;

		// get the neighbours for the user
		Set<Integer> neighbours = neighbourhood.getNeighbours(userId);

		// return null if the user has no neighbours
		if (neighbours == null)
			return null;

		for (Integer neighbour: neighbours) // iterate over each neighbour
		{
			Double rating = userProfileMap.get(neighbour).getValue(itemId); // get the neighbour's rating for the target item
			if(rating != null)
			{
				double sim = simMap.getSimilarity(userId, neighbour); // get the similarity between the target user and the neighbour
				above += rating.doubleValue() * sim;
				below += sim;
			}
		}

		if(below > 0)
			return above / below;
		else
			return null;
	}
}

