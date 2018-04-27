/* Distributed grep- filtering pattern
 * give the regex string while running map reduce job
 * Since it's a map only job look for output in part-m-00000
 *  */
import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class GrepDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public class GrepMapper extends Mapper<Object, Text, NullWritable, Text> {
		private String mapRegex = null;

		@Override
		protected void map(Object key, Text value,
				Mapper<Object, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			
			if(value.toString().matches(mapRegex)){
				context.write(NullWritable.get(), value);
			}
		}

		@Override
		protected void setup(
				Mapper<Object, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			mapRegex = context.getConfiguration().get("regex");
		}
		
		
	}
	
	

}
