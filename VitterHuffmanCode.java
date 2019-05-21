import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class VitterHuffmanCode {
	
	private final int m = 26;
	private final int r = 10;
	private final int e = 4;
	private int currentNodeNumber = 51;
	private ArrayList<TreeNodeAdaptiveHuffman> nodeLists = new ArrayList<TreeNodeAdaptiveHuffman>();
	private HashMap<Character, String> codes = new HashMap<Character, String>();
	public VitterHuffmanCode() {
		
		codes.put('a', "00000");
		codes.put('b', "00001");
		codes.put('c', "00010");
		codes.put('d', "00011");
		codes.put('e', "00100");
		codes.put('f', "00101");
		codes.put('g', "00110");
		codes.put('h', "00111");
		codes.put('i', "01000");
		codes.put('j', "01001");
		codes.put('k', "01010");
		codes.put('l', "01011");
		codes.put('m', "01100");
		codes.put('n', "01101");
		codes.put('o', "01110");
		codes.put('p', "01111");
		codes.put('q', "10000");
		codes.put('r', "10001");
		codes.put('s', "10010");
		codes.put('t', "10011");
		
		codes.put('u', "1010");
		codes.put('v', "1011");
		codes.put('w', "1100");
		codes.put('x', "1101");
		codes.put('y', "1110");
		codes.put('z', "1111");

	}

	public void encode(ReadStream in, WriteStream out, String treeFileName) throws IOException {
		TreeNodeAdaptiveHuffman nyt = initializeTree();
		nodeLists.add(nyt);
		currentNodeNumber--;
		HashMap<Character, TreeNodeAdaptiveHuffman> nodes = new HashMap<Character, TreeNodeAdaptiveHuffman>();
		while(in.hasNext()) {
			char remaining = in.nextChar();
			if(nodes.containsKey(remaining)) {
				TreeNodeAdaptiveHuffman nodeChar = nodes.get(remaining);
				nodeChar.setWeight(nodeChar.getWeight() + 1);
				out.write(traversePath(nodeChar).getBytes());
				updateTree(nodeChar);
			} else {
				TreeNodeAdaptiveHuffman nodeChar = new TreeNodeAdaptiveHuffman(1, remaining, null, null, null, -1);
				TreeNodeAdaptiveHuffman parent = new TreeNodeAdaptiveHuffman(0, '@', nyt, nodeChar, null, -1);
				out.write(traversePath(nyt).getBytes());
				TreeNodeAdaptiveHuffman tempNode = nyt.parent;
				if(tempNode != null) {
					tempNode.left = parent;
				}
				parent.parent = nyt.parent;
				parent.setNodeNumber(nyt.getNodeNumber());
				nodeChar.setNodeNumber(currentNodeNumber);
				currentNodeNumber--;
				nyt.setNodeNumber(currentNodeNumber);
				currentNodeNumber--;
				nodeChar.parent = parent;
				nyt.parent = parent;
				out.write(codes.get(remaining).getBytes());
				updateTree(nodeChar);
				nodes.put(remaining, nodeChar);
			}
//			System.out.println(nyt.parent.parent);
//			traversePath(nyt);
		}
//		TreeNodeAdaptiveHuffman node = nyt;
//		while(!node.isRoot()) {
//			node = node.parent;
//		}
//		writeTreeToFile(node, treeFileName);
		in.close();
		out.close();
	}
	
	public String traversePath(TreeNodeAdaptiveHuffman node) {
		StringBuffer sbf = new StringBuffer();
		ArrayList<TreeNodeAdaptiveHuffman> path = new ArrayList<TreeNodeAdaptiveHuffman>();
		while(!node.isRoot()) {
			path.add(node);
			node = node.parent;
		}
		Collections.reverse(path);
		if(path.size() > 0) {
			TreeNodeAdaptiveHuffman root = node;
			for(int i = 0;i<path.size();i++) {
				TreeNodeAdaptiveHuffman tempNode = path.get(i);
				if(root.left.getNodeNumber() == tempNode.getNodeNumber()) {
					sbf.append("0");
					root = root.left;
				} else {
					sbf.append("1");
					root = root.right;
				}
			}
		}
		return sbf.toString();

	}
	public TreeNodeAdaptiveHuffman initializeTree() {
		return new TreeNodeAdaptiveHuffman(0, '-', null, null, null, currentNodeNumber);
	}
	
	public void updateTree(TreeNodeAdaptiveHuffman node) {
		int indexNode = -1;
		for(int i = 0;i<nodeLists.size();i++) {
			TreeNodeAdaptiveHuffman tempNode = nodeLists.get(i);
			if(tempNode.getCharacter() == node.getCharacter()) {
				indexNode = i;
				break;
			}
		}
		if(indexNode == -1) {
			ArrayList<TreeNodeAdaptiveHuffman> temp2 = new ArrayList<TreeNodeAdaptiveHuffman>();
			temp2.add(nodeLists.get(0));
			temp2.add(node);
			for(int i = 2;i<nodeLists.size();i++) {
				temp2.add(nodeLists.get(i));
			}
			nodeLists = temp2;
			indexNode = 1;
		}
		
		while(indexNode < nodeLists.size()) {
			TreeNodeAdaptiveHuffman temp3 = nodeLists.get(indexNode);
			if(!temp3.isLeaf()) {
				temp3.setWeight(temp3.left.getWeight() + temp3.right.getCharacter());
			}
			for(int i = indexNode + 1;i<nodeLists.size();i++) {
				TreeNodeAdaptiveHuffman temp4 =  nodeLists.get(i);
				if(temp3.getWeight() <= temp4.getWeight()) {
					if(i - 1 != indexNode) {
						TreeNodeAdaptiveHuffman temp5 = temp3.parent;
						if(temp5.left.getCharacter() == temp3.getCharacter()) {
							temp5.left = temp4;
						} else {
							temp5.right = temp4;
						}
						
						if(temp4.parent.left.getCharacter() == temp4.getCharacter()) {
							temp4.parent.left = temp3;
						} else {
							temp4.parent.right = temp3;
						}
						temp3.parent = temp4.parent;
						temp4.parent = temp5;
					}
					break;
				}
				
			}
			indexNode++;
		}
		
		
		
 		
	}
	
	public void writeTreeToFile(TreeNodeAdaptiveHuffman root, String treeFileName) throws IOException {
		ArrayDeque<TreeNodeAdaptiveHuffman> dq = new ArrayDeque<TreeNodeAdaptiveHuffman>();
		dq.push(root);
		StringBuilder sb = new StringBuilder();
		writeTreToFileHelper(root, sb, dq);
		FileUtil.writeToFile(treeFileName, sb.toString().getBytes());
	}
	
	public void writeTreToFileHelper(TreeNodeAdaptiveHuffman root, StringBuilder sb, ArrayDeque<TreeNodeAdaptiveHuffman> dq) {
		TreeNodeAdaptiveHuffman parent = dq.pollLast();
		sb.append(parent.getNodeNumber() + "|" + parent.getWeight() + "|" + parent.getCharacter() + "|" 
				+ (parent.parent != null ? parent.parent.getNodeNumber() : -2) + "\n");
		if(!parent.isLeaf()) {
			dq.push(parent.left);
			dq.push(parent.right);
		}
		if(!dq.isEmpty()) {
			writeTreToFileHelper(root, sb, dq);
		}
	}
//	
//	public TreeNodeAdaptiveHuffman buildTreeFromFile(String filename) throws IOException {
//		String[] temp = new String(FileUtil.readAsByteArray(filename)).split("\n");
//		HashMap<Integer, TreeNodeAdaptiveHuffman> dict = new HashMap<Integer, TreeNodeAdaptiveHuffman>();
//		for(int i = 0;i<temp.length;i++) {
//			String[] temp2 = temp[i].split("\\|");
//			int nodeNumber = Integer.parseInt(temp2[0]);
//			int weight = Integer.parseInt(temp2[1]);
//			char character = temp2[2].charAt(0);
//			int parentNodeNumber = Integer.parseInt(temp2[3]);
//			if(parentNodeNumber == -2) {
//				dict.put(nodeNumber, new TreeNodeAdaptiveHuffman(weight, character, null, null, null, -2));
//			} else {
//				TreeNodeAdaptiveHuffman tempp = new TreeNodeAdaptiveHuffman(weight, character, null, null, null, parentNodeNumber);
//				TreeNodeAdaptiveHuffman tempParent = dict.get(parentNodeNumber);
//				tempp.parent = tempParent;
//				if(tempParent.left == null) {
//					tempParent.left = tempp;
//				} else {
//					tempParent.right = tempp;
//				}
//			}
//		}
//		return dict.get(51);
//	}
	
	public void decode(ReadStream in, WriteStream out) throws IOException {
		int BUFFER_SIZE = 8 * 1024;
		StringBuffer sbf = new StringBuffer();
		TreeNodeAdaptiveHuffman nyt = new TreeNodeAdaptiveHuffman(0, '@', null, null, null, currentNodeNumber);
		nodeLists.add(nyt);
		TreeNodeAdaptiveHuffman root = nyt;
		while(in.hasNext()) {
			while(!root.isLeaf()) {
				char remaining = 0;
				if(in.hasNext()) {
					remaining = in.nextChar();
				}
				if(remaining == '0') {
					root = root.left;
				} else {
					root = root.right;
				}
				
			}

			if(root.getWeight() == 0) {
				StringBuffer sb = new StringBuffer();
				char remaining;
				for(int i = 0;i<e;i++) {
					if(in.hasNext()) {
						remaining = in.nextChar();
						sb.append(remaining);
					}
				}
				int num = Integer.parseInt(sb.toString(), 2);
				if(num < r) {
					if(in.hasNext()) {
						remaining = in.nextChar();
						sb.append(remaining);
					}
//					System.out.println(sb);
					num = Integer.parseInt(sb.toString(), 2);
				} else {
					num += r;
				}
//				System.out.println(num);
				char character = (char) ((num) + 'a');
				sbf.append(character);
				TreeNodeAdaptiveHuffman nodeChar = new TreeNodeAdaptiveHuffman(1, character, null, null, null, -1);
				TreeNodeAdaptiveHuffman parent = new TreeNodeAdaptiveHuffman(0, '@', root, nodeChar, null, -1);
				TreeNodeAdaptiveHuffman tempNode = root.parent;
				if(tempNode != null) {
					tempNode.left = parent;
				}
				parent.parent = root.parent;
				parent.setNodeNumber(root.getNodeNumber());
				nodeChar.setNodeNumber(currentNodeNumber);
				currentNodeNumber--;
				root.setNodeNumber(currentNodeNumber);
				currentNodeNumber--;
				nodeChar.parent = parent;
				root.parent = parent;
				updateTree(nodeChar);
			} else {
				sbf.append(root.getCharacter());
				root.setWeight(root.getWeight() + 1);
				updateTree(root);
			}
			while(!root.isRoot()) {
				root = root.parent;
			}
			if(sbf.length() >= BUFFER_SIZE) {
				out.write(sbf.toString().getBytes());
				sbf = new StringBuffer();
			}
		}
		out.write(sbf.toString().getBytes());	
		out.close();
		in.close();
	}
}
