
public class recommedBooksToUsers {

	public static void main(String[] args) {
	       try {
	            //STEP 1. Create the DataModel
	            DataModel model = new FileDataModel(new File("C:/dataset.csv"));
	            
	            //STEP 2. Find Similar users
	            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	            
	            //STEP 3. Define neighborhood
	            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
	            
	            //STEP 4. Build a Recommender Engine
	            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	            
	            //STEP 5. Ask the Recommender Engine for Recommendation
	            List<RecommendedItem> recommendations = recommender.recommend(2, 1);
	            
	            for (RecommendedItem item : recommendations)
	                System.out.println("Recommended Item: " + item);
	        } catch (IOException ex) {
	            System.out.println("EXCEPTION: " + ex.getMessage());
	        } catch (TasteException ex) {
	            System.out.println("EXCEPTION: " + ex.getMessage());
	        }
	        

	}

}
