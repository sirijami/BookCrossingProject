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



public class FrequencyOfBookPublishedEachYearDriver {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		
		Job job = Job.getInstance();
		job.setMapperClass(FrequencyOfBookPublishedEachYearMapper.class);
		job.setCombinerClass(FrequencyOfBookPublishedEachYearReducer.class);
		job.setReducerClass(FrequencyOfBookPublishedEachYearReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	
	public static class FrequencyOfBookPublishedEachYearMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split("\";\"");
			if(!tokens[3].equals("Year-Of-Publication")){
				context.write(new Text(tokens[3]), new IntWritable(1));
			}

		}		
	}
	
	public static class FrequencyOfBookPublishedEachYearReducer extends Reducer<Text, IntWritable,
	Text, IntWritable>{

		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			int frequency = 0;
			for(IntWritable val : arg1){
				frequency = frequency+ val.get();
			}
			context.write(arg0, new IntWritable(frequency));			
		}		
	}

}
