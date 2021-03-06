package MahoutRecommendation.RecommendBooks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookCrossingDataConvert {
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/sirishaepari/Desktop/BigData/BigDataSets"
				+ "/BX-CSV-Dump/BX-Book-Ratingscopy.csv"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/sirishaepari/Desktop/BigData/BigDataSets"
				+ "/BX-CSV-Dump/formatedBookRatings.csv"));
		
		String record;
		while((record = br.readLine()) != null){
			String replaceQuotes = record.replaceAll("\"", "");
			String[] tokens = replaceQuotes.split(";", -1);
			try{
				bw.write(Long.parseLong(tokens[0]) + "," + Long.parseLong(tokens[1]) + "," + Long.parseLong(tokens[2])+ "\n");
			}catch(Exception e){
				continue;
			}		
		}

		br.close();
		bw.close();	
		

	}

}
