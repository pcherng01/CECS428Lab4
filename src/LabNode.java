import java.util.HashMap;

public class LabNode {
	int mVertexNum;
	HashMap<Integer, Integer> mNeighbors;

	public LabNode(int pVertexNum) {
		mVertexNum = pVertexNum;
		mNeighbors = new HashMap<Integer, Integer>();
	}

	public void addNeighbor(int pNeighborVertexNum, int pWeight) {
		mNeighbors.put(pNeighborVertexNum, pWeight);
	}

	public int getNeighborSize() {
		return mNeighbors.size();
	}
}