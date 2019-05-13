
public class TreeNodeHuffman implements Comparable<TreeNodeHuffman> {
	private int frequency;
	private char value;
	public TreeNode left;
	public TreeNode right;

	public TreeNode(int frequency, char value, TreeNodeHuffman left, TreeNodeHuffman right) {
		this.frequency = frequency;
		this.value = value;
	}

	public TreeNode(char value) {
		this.value = value;
	}

	public TreeNode() {}

	public void setValue(char value) {
		this.value = value;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public char getValue() {
		return this.value;
	}

	public int getFrequency() {
		return this.frequency;
	}


	public boolean isLeaf() {
		return left == null && right == null;
	}

	public int compareTo(TreeNodeHuffman node) {
		return this.freq - node.getFrequency();
	}

}