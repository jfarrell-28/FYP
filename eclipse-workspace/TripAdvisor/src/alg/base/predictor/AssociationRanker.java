/**
 * Compute the rank score between two items based on item association
 */ 

package alg.base.predictor;

import util.reader.DatasetReader;

public class AssociationRanker implements Ranker
{
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items 

	/**
	 * constructor - creates a new AssociationRanker object
	 */
	public AssociationRanker()
	{}

	/**
	 * given item X, computes the rank score for item Y
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 * @param reader - a DatasetReader object
	 */
	@Override
	public double getRankScore(final Integer X, final Integer Y, final DatasetReader reader)
	{
		// implement this method
		// do *not* round the double value that is returned
		return 0;
	}
}
