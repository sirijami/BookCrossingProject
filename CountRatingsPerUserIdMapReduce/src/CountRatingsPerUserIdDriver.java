


import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class CountRatingsPerUserIdDriver {

	public static void main(String[] args) throws IllegalArgumentException, IOException, InterruptedException, ClassNotFoundException {
		/* Create a Job */
		Job job = Job.getInstance();

		/* Create map and reduce class */
		job.setMapperClass(CountRatingsPerUserIdMapper.class);
		
        /* Set Combiner */
		job.setCombinerClass(CountRatingsPerUserIdReducer.class);
		job.setReducerClass(CountRatingsPerUserIdReducer.class);
		
		/* Set output key and value class */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		/* Set Input and output format classes */
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	
		
		/* Set input & output format */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	
	public static class CountRatingsPerUserIdMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split(";");
			if(!tokens[0].equals("User-ID")){
				context.write(new Text(tokens[0]), new IntWritable(1));
			}

		}		
	}
	
	public static class CountRatingsPerUserIdReducer extends Reducer<Text, IntWritable,
	Text, IntWritable>{

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable val : arg1){
				sum = sum + val.get();
			}
			context.write(arg0, new IntWritable(sum));			
		}		
	}

}
