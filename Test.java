import java.io.File;
import java.io.IOException;


public class Test {	
	public static void main(String[] args) throws IOException {
		String samplePath = "/home/rizqy/workspace/PaperDAA2/src/large_x.txt";
		File raw2 = new File(samplePath);
		System.out.println(raw2.length());
		long decodedTime = 0;
		long encodedTime = 0;
		long decodedMemory = 0;
		long encodedMemory = 0;
		double ratio = 0;
		System.gc();
		Runtime rt = Runtime.getRuntime();
		for(int i = 0;i<1;i++) {
//			ReadStream stream = new ReadStream(samplePath, 8192);
//			WriteStream streamOut = new WriteStream(samplePath + "__encoded", 8192);
//			VitterHuffmanCode hc = new VitterHuffmanCode();
//			long before = System.nanoTime();
//			hc.encode(stream, streamOut, samplePath + "__dict_huffman");
//			long after = System.nanoTime();
//			
//			encodedTime += ((after - before) / 1000000);
//		    long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
//		    encodedMemory += usedMB;

			
			ReadStream stream2 = new ReadStream(samplePath + "__encoded", 8192);
			WriteStream streamOut2 = new WriteStream(samplePath + "__decoded", 8192);
		    VitterHuffmanCode hc2 = new VitterHuffmanCode();
			long before2 = System.nanoTime();
			hc2.decode(stream2, streamOut2);		
			long after2 = System.nanoTime();

			decodedTime += ((after2 - before2) / 1000000);
			long usedMB2 = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
		    decodedMemory += usedMB2;
//			
			File raw = new File(samplePath);
			File decoded = new File(samplePath + "__encoded");
			
			ratio += (raw.length() - (decoded.length() / 8)) / (double) raw.length();
//			byte[] data1 = FileUtil.readAsByteArray(samplePath + "__decoded");
//			byte[] data2 = FileUtil.readAsByteArray(samplePath);
//			System.out.println(isSimilar(data1, data2));
		}
		
		System.out.println(encodedTime / 1);
		System.out.println(decodedTime / 1);
		System.out.println(encodedMemory / 1);
		System.out.println(decodedMemory / 1);
		System.out.println(ratio / 1);
		
	}
	
	public static boolean isSimilar(byte[] data1, byte[] data2) {
		if(data1.length != data2.length) {
			return false;
		}
		
		for(int i = 0;i<data1.length;i++) {
			if(data1[i] != data2[i]) {
				return false;
			}
		}
		return true;
	}
}
