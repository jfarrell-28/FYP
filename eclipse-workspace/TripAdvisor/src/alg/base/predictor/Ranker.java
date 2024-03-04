/**
 * An interface to compute the rank score for items.
 * For example, given item X, the rank score of item Y is calculated.
 * Recommendations for item X are ranked based on this score.
 */

package alg.base.predictor;

import util.reader.DatasetReader;

public interface Ranker 
{
	/**
	 * given item X, computes the rank score for item Y
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 * @param reader - a DatasetReader object
	 */
	public double getRankScore(final Integer X, final Integer Y, final DatasetReader reader);
}
