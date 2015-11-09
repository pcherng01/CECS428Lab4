import java.util.PriorityQueue;

public class Node {
	int vertexNum;
	boolean visited;
	// PriorityQueue for storing the neighbor with the minimum edge as highest priority
	PriorityQueue<Pair> pq1;
	// After popping the minimum element, it will be store in pq2
	PriorityQueue<Pair> pq2;

	public Node(int pVertexNum) {
		vertexNum = pVertexNum;
		visited = false;
		pq1 = new PriorityQueue<Pair>();
		pq2 = new PriorityQueue<Pair>();
	}

	public void addNeighbor(int pNeighborNum, int pWeighEdge) {
		pq1.add(new Pair(pNeighborNum, pWeighEdge));
	}

	/**
	 * Get the vertex number of the neighbor with the minimum Edge
	 * 
	 * @return - neighbor vertexNumber, -1 if the queue is empty
	 */
	public Pair getMinNeighbor() {
		Pair removedPair = pq1.remove();
		pq2.add(removedPair);
		return removedPair;
	}

	public void resetPQ() {
		while (pq2.peek() != null) {
			pq1.add(pq2.remove());
		}
		pq2 = new PriorityQueue<Pair>();
	}

	public boolean hasMinNeighbor() {
		return (pq1.size() != 0);
	}

	static class Pair implements Comparable {
		int vertexNum;
		int weightValue;

		public Pair(int pKey, int pWeight) {
			vertexNum = pKey;
			weightValue = pWeight;
		}

		@Override
		public int compareTo(Object o) {
			Pair compPair = (Pair) o;
			return this.weightValue - compPair.weightValue;
		}
	}
}
