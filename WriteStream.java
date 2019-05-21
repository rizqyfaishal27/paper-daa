import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class WriteStream {
	private BufferedOutputStream out;

	public WriteStream(String filename, int bufferSize) throws IOException {
		this.out = new BufferedOutputStream(new FileOutputStream(filename), bufferSize);
	}
	
	public void write(byte[] data) throws IOException {
		out.write(data);
	}
	
	public void close() throws IOException {
		out.flush();
		out.close();
	}
	
	
	
}
