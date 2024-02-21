/**
 * A class to implement a non-personalised recommender
 */

package alg.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import alg.base.ranker.Ranker;
import utill.Matrix;
import util.reader.DatasetReader;

public class BaseRecommender {
	private Matrix matrix; // used to store all item-item rank scores

	/**
	 * constructor - creates a new NPRecommender object
	 * @param reader - dataset reader
	 * @param ranker - the ranker used to rank items
	 */
	public BaseRecommender(final DatasetReader reader, final Ranker ranker) {
		matrix = new Matrix();
		
		// get the set of item ids
		Set<Integer> itemIds = reader.getItems().keySet();

		// for each item, compute the rank scores of all other items
		for(Integer id1: itemIds)
			for(Integer id2: itemIds)
				if(id2 < id1) {
					if(ranker instanceof alg.base.ranker.GenreRanker ||
							ranker instanceof alg.np.ranker.GenomeRanker ||
							ranker instanceof alg.np.ranker.RatingRanker) { // rank scores are symmetric
						double score = ranker.getRankScore(id1, id2, reader);
						if (score > 0) {
							matrix.addValue(id1, id2, score);
							matrix.addValue(id2, id1, score);
						}
					} else if (ranker instanceof alg.np.ranker.AssociationRanker) { // rank scores are not symmetric
						double score = ranker.getRankScore(id1, id2, reader);
						if(score > 1) matrix.addValue(id1, id2, score);
						
						score = ranker.getRankScore(id2, id1, reader);
						if(score > 1) matrix.addValue(id2, id1, score);
					} else {
						System.out.println("Error - invalid ranker");
						System.exit(1);
					}
				}
	}

	/**
	 * @returns the recommendations for the target item
	 * @param itemId - the target item ID
	 */
	public List<Integer> getRecommendations(final Integer itemId)
	{	
		// create a list to store recommendations
		List<Integer> recs = new ArrayList<Integer>();

		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); // store all items in order of descending rank score in a sorted set

		Set<Integer> ids = matrix.getColIds(itemId); // get the items with non-zero rank scores for the specified item
		for (Integer id: ids)
		{
			double score = matrix.getValue(itemId, id);
			if (score > 0)
				ss.add(new ScoredThingDsc(score, id));
		}


		// store all recommended items in descending order of rank score in the list
		for(Iterator<ScoredThingDsc> iter = ss.iterator(); iter.hasNext(); )
		{
			ScoredThingDsc st = iter.next();
			Integer id = (Integer)st.thing;
			recs.add(id);
		}

		return recs;
	}
}
