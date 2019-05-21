
public class TreeNodeHuffman implements Comparable<TreeNodeHuffman> {

	private int frequency;
	private char character;
	public TreeNodeHuffman left;
	public TreeNodeHuffman right;
	
	public TreeNodeHuffman(int frequency, char character, TreeNodeHuffman left,
			TreeNodeHuffman right) {
		this.frequency = frequency;
		this.character = character;
		this.left = left;
		this.right = right;
	}


	public int getFrequency() {
		return frequency;
	}


	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}


	public char getCharacter() {
		return character;
	}


	public void setCharacter(char character) {
		this.character = character;
	}

	@Override
	public int compareTo(TreeNodeHuffman that) {
		// TODO Auto-generated method stub
		System.out.println(this.frequency - that.getFrequency());
		return this.frequency - that.getFrequency();
	}
	
	public String toString() {
		return this.frequency + "";
	}
	
	public boolean isLeaf() {
		return left == null && right == null;
	}
		
	
}
