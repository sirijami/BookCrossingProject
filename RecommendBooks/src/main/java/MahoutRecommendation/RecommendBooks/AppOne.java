package MahoutRecommendation.RecommendBooks;



import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;

public class AppOne {

	public static void main(String[] args) {
	       try {
	            //STEP 1. Create the DataModel
	            DataModel model = new FileDataModel(new File("/Users/sirishaepari/Desktop/BigData/BigDataSets"
					+ "/BX-CSV-Dump/formatedBookRatings.csv"));
	            
	            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	            
	            //STEP 3. Define neighborhood
	            UserNeighborhood neighborhood = new NearestNUserNeighborhood(276861, similarity, model);
	            
	            //STEP 4. Build a Recommender Engine
	            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	            
	            //STEP 5. Ask the Recommender Engine for Recommendation
	            List<RecommendedItem> recommendations = recommender.recommend(276861, 1);
	            
	            for (RecommendedItem item : recommendations) 
	                System.out.println("Recommended Item: " + item); 
	        } catch (IOException ex) {
	            System.out.println("EXCEPTION: " + ex.getMessage());
	        } catch (TasteException ex) {
	            System.out.println("EXCEPTION: " + ex.getMessage());
	        }
	        

	}

}
