package alg.similarity;

import java.util.Set;

import utill.Profile;

public class CosineSimilarity implements SimilarityCalculator
{
    public CosineSimilarity()
    {
    	
    }
    
    // Function to compute cosine similarity between two vectors represented as HashMaps
    public double getSimilarity(final Profile p1, final Profile p2)
	{
        double dotProduct = 0;
        
        Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			dotProduct += r1 * r2;
		}

		double n1 = p1.getNorm();
		double n2 = p2.getNorm();
		return (n1 > 0 && n2 > 0) ? dotProduct / (n1 * n2) : 0;
	}
}