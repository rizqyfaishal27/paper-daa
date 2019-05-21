import java.io.*;
import java.util.*;

public class DataGenerator {

	public static void main(String[] args) throws IOException{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(args[0]);
		Random rand = new Random(System.currentTimeMillis());
		Random randSeed = new Random();
		int[] pp = new int[26];
		for(int i = 0;i<n;i++) {
			rand = new Random(randSeed.nextLong());
			int u = rand.nextInt(7);
			out.write('a' + u);
			pp[u]++;
		}
		for(int i = 0;i<pp.length;i++) {
			System.out.println( ((char) (i +'a')) + " = " + pp[i]);
		}
		out.write('\n');
		out.flush();
	}
}
