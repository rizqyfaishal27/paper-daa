
public class TreeNodeAdaptiveHuffman implements Comparable<TreeNodeAdaptiveHuffman>{
	private int weight;
	private char character;
	private int nodeNumber;
	public TreeNodeAdaptiveHuffman left;
	public TreeNodeAdaptiveHuffman right;
	public TreeNodeAdaptiveHuffman parent;
	
	
	public TreeNodeAdaptiveHuffman(int weight, char character,
			TreeNodeAdaptiveHuffman left, TreeNodeAdaptiveHuffman right, TreeNodeAdaptiveHuffman parent, int nodeNumber) {
		this.weight = weight;
		this.character = character;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.nodeNumber = nodeNumber;
	}

	public int getWeight() {
		return weight;
	}
	
	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	public boolean isRoot() {
		return parent == null;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public String toString() {
		return "" + this.nodeNumber;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	@Override
	public int compareTo(TreeNodeAdaptiveHuffman that) {
		// TODO Auto-generated method stub
		return this.weight - that.getWeight();
	}
	
	
	
	
}
