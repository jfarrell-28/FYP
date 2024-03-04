package alg.base;

import java.io.File;

import util.reader.*;
import alg.base.predictor.Predictor;
import alg.base.predictor.WeightedAverage;
import alg.similarity.*;
import alg.Evaluator.Evaluator;
import alg.base.neighbourhood.*;

public class ExecuteBase 
{
	public static void main(String[] args)
	{
		Predictor predictor = new WeightedAverage();
		Neighbourhood neighbourhood = new NearestNeighbour(10);
		SimilarityCalculator metric = new CosineSimilarity();
		
		String folder = "C:\\Users\\Jobby\\Desktop\\College\\Final Year Project\\TripAdvisorDataset\\";
		String itemFile = folder + File.separator + "members.csv";
		String trainFile = folder + File.separator + "hotels.csv";
		String testFile = folder + File.separator + "reviews.csv";
		
		String outputFile = "results" + File.separator + "predictions.txt";
		
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
		UserBasedCF userBased = new UserBasedCF(predictor, neighbourhood, metric, reader);
	
		Evaluator eval = new Evaluator(userBased, reader.getTestData());
		eval.writeResults(outputFile);
		Double RMSE = eval.getRMSE();
		double coverage = eval.getCoverage();
		System.out.printf("RMSE: %.4f\nCoverage: %.2f%c\n", RMSE, coverage, '%');
	}
}
