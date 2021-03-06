import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AverageRatingOfEachBookDriver {

	public static void main(String[] args) throws IllegalArgumentException, IOException, InterruptedException, ClassNotFoundException {
				
		Job job = Job.getInstance();
		job.getConfiguration().set("mapreduce.output.textoutputformat.separator", ",");
		job.setMapperClass(AverageRatingOfEachMapper.class);
		job.setCombinerClass(AverageRatingOfEachReducer.class);
		job.setReducerClass(AverageRatingOfEachReducer.class);

		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(countAverageTuple.class);
		
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	
	public static class AverageRatingOfEachMapper extends Mapper<LongWritable, Text, Text, countAverageTuple>{

		@Override
		protected void map(
				LongWritable key,
				Text value,
				Mapper<LongWritable, Text, Text, countAverageTuple>.Context context)
				throws IOException, InterruptedException {
			
			countAverageTuple outCountAverage = new countAverageTuple();
			
			String[] temp = value.toString().split(";");
			String rating = temp[2];
			rating = rating.replaceAll("\"", "");

			if(!rating.equals("Book-Rating")){
				outCountAverage.setCount(1);
				outCountAverage.setAverage(Float.parseFloat(rating));
				context.write(new Text(temp[1]), outCountAverage);	
			}

		}		
	}
	
	public static class AverageRatingOfEachReducer extends Reducer<Text, countAverageTuple, Text, countAverageTuple>{

		@Override
		protected void reduce(
				Text arg0,
				Iterable<countAverageTuple> arg1,
				Reducer<Text, countAverageTuple, Text, countAverageTuple>.Context context)
				throws IOException, InterruptedException {
			countAverageTuple result = new countAverageTuple();
			float sum = 0;
			int count = 0;
			
			for(countAverageTuple v: arg1){
				sum += v.getCount() * v.getAverage();
				count = count + v.getCount();				
			}
			result.setCount(count);
			result.setAverage(sum/count);
			
			context.write(arg0, result);
		}
				
	}

}
