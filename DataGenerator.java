import java.io.*;
import java.util.*;

public class DataGenerator {

	public static void main(String[] args) throws IOException{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(args[0]);
		for(int i = 0;i<n;i++) {
			int u = (int) Math.floor(Math.random() * 26);
			out.write('a' + u);
		}
		out.write('\n');
		out.flush();
	}
}