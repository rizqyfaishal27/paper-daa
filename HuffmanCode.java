import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;


public class HuffmanCode {
	
	private final  int CHAR_SIZE = 26;
	
	public void encode(ReadStream in, WriteStream out, String dictFileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		while(in.hasNext()) {
			sb.append(in.nextChar());
		}
		char[] data = sb.toString().toCharArray();
		int[] freq = getStatisticsData(data);
		TreeNodeHuffman root = buildTree(freq);
		HashMap<Character, StringBuilder> codes= createDictCodes(root);
//		String table = getDictCodesTable(codes);
		for(int i = 0;i<data.length;i++) {
			out.write(codes.get(data[i]).toString().getBytes());
		}
		generateDictFile(freq, codes, dictFileName);
		in.close();
		out.close();
	}
	
	public void generateDictFile(int[] freq, HashMap<Character, StringBuilder> codes, String dictFileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<freq.length;i++) {
			if(codes.containsKey((char) (i + 'a'))) {
				sb.append((char) (i+'a') + "|");
				sb.append(codes.get((char) (i + 'a')).toString());
				sb.append("\n");
			}
		}
		FileUtil.writeToFile(dictFileName, sb.toString().getBytes());
	}
	
	public int[] getStatisticsData(char[] data) {
		int[] freq = new int[CHAR_SIZE];
		for(int i = 0;i<data.length;i++) {
			freq[data[i] - 'a']++;
		}
		return freq;
	}
	
	public TreeNodeHuffman buildTree(int[] freq) {
		PriorityQueue<TreeNodeHuffman> pq = new PriorityQueue<TreeNodeHuffman>(new Comparator<TreeNodeHuffman>() {

			@Override
			public int compare(TreeNodeHuffman o1, TreeNodeHuffman o2) {
				// TODO Auto-generated method stub
				return o1.getFrequency() - o2.getFrequency();
			}
			
		});
		
		for(int i = 0;i<freq.length;i++) {
			if(freq[i] > 0) {
				char p = (char) ('a' + i);
				pq.add(new TreeNodeHuffman(freq[i], p, null, null));
			}
		}
		
		while(pq.size() > 1) {
			TreeNodeHuffman left = pq.poll();
			TreeNodeHuffman right = pq.poll();
			TreeNodeHuffman parent = new TreeNodeHuffman(left.getFrequency() + right.getFrequency(), '@', left, right);
			pq.add(parent);
		}
		
		return pq.poll();
	}
	
	public HashMap<Character, StringBuilder> createDictCodes(TreeNodeHuffman root) {
		StringBuilder path = new StringBuilder();
		HashMap<Character, StringBuilder> codes = new HashMap<Character, StringBuilder>();
		createDictCodesHelper(root, path, codes);
		return codes;
	}
	
	public String getDictCodesTable(HashMap<Character, StringBuilder> codes) {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		
		sb1.append("char   | binary codes\n");
		sb2.append("-------|");
		int maxCodeLength = 0;
		Set<Character> keys = codes.keySet();
		Iterator<Character> it = keys.iterator();
		while(it.hasNext()) {
			char key = it.next();
			if(codes.get(key).length() > maxCodeLength) {
				maxCodeLength = codes.get(key).length();
			}
		}
		for(int i = 0;i<maxCodeLength + 2;i++) {
			sb2.append('-');
		}
		sb2.append("\n");
		Iterator<Character> it2 = keys.iterator();
		while(it2.hasNext()) {
			char key = it2.next();
			sb3.append(key);
			sb3.append("      | ");
			sb3.append(codes.get(key).toString());
			sb3.append(" \n");
		}
		return sb1.append(sb2.toString()).append(sb3.toString()).toString();
	}
	
	public void createDictCodesHelper(TreeNodeHuffman root, StringBuilder path, HashMap<Character, StringBuilder> codes) {
		if(root.isLeaf()) {
			codes.put(root.getCharacter(), path);
			return;
		}
		StringBuilder leftPath = new StringBuilder(path.toString());
		StringBuilder rightPath = new StringBuilder(path.toString());
		leftPath.append(1);
		createDictCodesHelper(root.left, leftPath, codes);
		rightPath.append(0);
		createDictCodesHelper(root.right, rightPath, codes);
	}

	public static TreeNodeHuffman fileToTree(String filename) throws IOException {
		String[] input = new String(FileUtil.readAsByteArray(filename)).split("\n");
		TreeNodeHuffman root = new TreeNodeHuffman(0, '@', null, null);
		for(int i = 0;i<input.length;i++) {
			String[] temp = input[i].split("\\|");
			char key = temp[0].toCharArray()[0];
			char[] path = temp[1].toCharArray();
			TreeNodeHuffman parent = root;
			for(int j = 0;j<path.length;j++) {
				if(path[j] == '0') {
					TreeNodeHuffman left = parent.left;
					if(left == null) {
						left = new TreeNodeHuffman(0, '@' , null, null);
					}
					parent.left = left;
					parent = left;
				} else {
					TreeNodeHuffman right = parent.right;
					if(right == null) {
						right = new TreeNodeHuffman(0, '@' , null, null);
					}
					parent.right = right;
					parent = right;
				}
			}
			parent.setCharacter(key);
		}
		return root;
	}
	
	public void decode(ReadStream in, WriteStream out, TreeNodeHuffman tree) throws IOException {
		TreeNodeHuffman parent = tree;
		int BUFFER_SIZE = 8 * 1024;
		StringBuffer bf = new StringBuffer(BUFFER_SIZE);
		while(in.hasNext()) {
			char remaining = in.nextChar();
			if(remaining == '0') {
				parent = parent.left;
			} else {
				parent = parent.right;
			}
			
			if(parent.isLeaf()) {
				bf.append(parent.getCharacter());
				parent = tree;
			}
			
			if(bf.length() == BUFFER_SIZE) {
				out.write(bf.toString().getBytes());
				bf = new StringBuffer();
			}
		}
		
		if(bf.length() > 0) {
			out.write(bf.toString().getBytes());
		}
		in.close();
		out.close();
	}
}
