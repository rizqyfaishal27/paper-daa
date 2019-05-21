import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;


public class FileUtil {
	
	public static byte[] readAsByteArray(String filename) throws IOException {
		return Files.readAllBytes(Paths.get(filename));
	}
	
	public static void writeToFile(String filename, byte[] data) throws IOException {
		FileOutputStream os = new FileOutputStream(new File(filename));
		os.write(data);
		os.close();
	}
	
	public static byte[] convertToByteArray(boolean[] bools) {
		BitSet bs = new BitSet();
		for(int i = 0;i<bools.length;i++) {
			bs.set(i, bools[i]);
		}
		return bs.toByteArray();
	}
	
}
