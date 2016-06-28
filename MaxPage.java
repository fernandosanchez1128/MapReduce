import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxPage {
  private  static int max_acount=0;	
  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text page = new Text();
    private String server;

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      server =  itr.nextToken();
      itr.nextToken();
      itr.nextToken();
      itr.nextToken();
      page.set(itr.nextToken());
      if (server.equals("edams.ksc.nasa.gov"))
		{context.write(page, one);}
    }
  }

 public static class MaxReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();
    Text server =new Text();
    
    public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {  sum += val.get();      }

      if(sum>max_acount)
      {
		max_acount=sum;
		server.set(key);
      }
    }

    protected void cleanup(Context context) throws IOException, InterruptedException{
    context.write(server, new IntWritable(max_acount));
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(MaxPage.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(MaxReducer.class);
    job.setReducerClass(MaxReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
