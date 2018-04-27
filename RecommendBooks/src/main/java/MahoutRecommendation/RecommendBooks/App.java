package MahoutRecommendation.RecommendBooks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveArrayIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Recommends a User, list of Books which they might like. 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            //STEP 1. Create the DataModel
            DataModel model = new FileDataModel(new File("/Users/sirishaepari/Desktop/BigData/BigDataSets"
				+ "/BX-CSV-Dump/formatedBookRatings.csv"));
      	  BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/sirishaepari/Desktop/BigData/BigDataSets/booksUserRecommendation.csv"));
      	  bw.write("User based Recommendation Euclidean Similarity" + "\n");
            
            //STEP 2. Find Similar users
            UserSimilarity similarity = new org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity(model);

            
            //STEP 3. Define neighborhood
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            
            //STEP 4. Build a Recommender Engine
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            int x = 1;
            for(LongPrimitiveArrayIterator users = (LongPrimitiveArrayIterator) model.getUserIDs();
            		users.hasNext();){
            	long userId = users.nextLong(); 
            	bw.write("for userid " + userId + "\n");
            	List<RecommendedItem> recommendations = recommender.recommend(userId, 5);
            	for(RecommendedItem rec: recommendations){
            		bw.write(rec.getItemID() + " ," + rec.getValue() + "\n");
            	}
            	x++;
            	if(x > 10){
            		break;
            	}
            }
          	bw.close();

        } catch (IOException ex) {
            System.out.println("EXCEPTION: " + ex.getMessage());
        } catch (TasteException ex) {
            System.out.println("EXCEPTION: " + ex.getMessage());
        }
        
    }
}
