import java.io.IOException;
import java.util.Collections;
import java.util.TreeMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class ToptenLeastUserGivenRatingsDriver {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		Job job = Job.getInstance();
		job.setNumReduceTasks(1);
		
		job.setJarByClass(ToptenLeastUserGivenRatingsDriver.class);
		job.setMapperClass(TopTenLeastUserGivenRatingMapper.class);

		job.setReducerClass(TopTenLeastUserGivenRatingReducer.class);
		

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
			
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	
	public static class TopTenLeastUserGivenRatingMapper extends Mapper<Object, Text, NullWritable, Text>{
		
		private TreeMap<Integer,Text> map = new TreeMap<Integer, Text>(Collections.reverseOrder());

		
		@Override
		protected void map(Object key, Text value,
				Mapper<Object, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split("\t");

			int numOfRatingsGiven = Integer.parseInt(tokens[1]);
			
			map.put(numOfRatingsGiven, new Text(value));
			
			if(map.size() > 10){
				map.remove(map.firstKey());
			}
		}
		@Override
		protected void cleanup(
				Mapper<Object, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			for(Text m : map.values()){
				context.write(NullWritable.get(), m);				
			}

		}		
	}
	
	public static class TopTenLeastUserGivenRatingReducer extends Reducer<NullWritable, Text, NullWritable, Text>{
		private TreeMap<Integer,Text> map = new TreeMap<Integer, Text>(Collections.reverseOrder());

		@Override
		protected void reduce(NullWritable arg0, Iterable<Text> arg1,
				Reducer<NullWritable, Text, NullWritable, Text>.Context arg2)
				throws IOException, InterruptedException {
			
			for(Text v : arg1){
				String[] t = v.toString().split("\t");	
				map.put(Integer.parseInt(t[1]), new Text(v));
				
				if(map.size() > 10){
					map.remove(map.firstKey());
				}
			}
		}
		
		@Override
		protected void cleanup(
				Reducer<NullWritable, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException{
				for(Text t : map.values()){
					context.write(NullWritable.get(), t);
				}
		}

		
		
	}
		
}



