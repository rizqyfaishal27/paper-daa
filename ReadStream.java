import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class ReadStream {
	private BufferedInputStream in;
	private int current;

	public ReadStream(String filename, int bufferSize) throws IOException {
		this.in = new BufferedInputStream(new FileInputStream(filename), bufferSize);
		this.current = in.read();
	}
	
	public boolean hasNext() throws IOException {
		return current != -1;
	}
	
	private int next() throws IOException {
		int temp = current;
		current = in.read();
		return temp;	
	}
	
	public char nextChar() throws IOException {
		return (char) next();
	}
		

	public void close() throws IOException {
		in.close();
	}
	
	
}
