import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class TestClass {

	public static void main(String[] args) {
		//		// TODO Auto-generated method stub
		//		TreeMap<Integer, Integer> aHM = new TreeMap();
		//		aHM.put(34, 34);
		//		aHM.put(19, 19);
		//		aHM.put(26, 26);
		//		aHM.remove(19);
		//		System.out.println(aHM.firstKey());
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		//
		//		Node[] nodeArr = { node1, node2, node3 };
		//		ArrayList<Node> aList = new ArrayList<Node>();
		//		aList.add(node1);
		//		aList.add(node2);
		//		aList.add(node3);
		//		nodeArr[0].data = 5;
		//		System.out.println(node1.data);
		//		for (Node eachNode : nodeArr) {
		//			System.out.println(eachNode.data);
		//		}

		//		PriorityQueue<Node> aPQ = new PriorityQueue();
		//		aPQ.add(node3);
		//		aPQ.add(node1);
		//		aPQ.add(node2);
		//		while (!aPQ.isEmpty()) {
		//			System.out.println(aPQ.remove().data);
		//		}
		//		Stack<Node> nodeStack = new Stack<Node>();
		//		HashSet<Node> nodeSet = new HashSet<Node>();
		//		nodeStack.push(node1);
		//		nodeStack.push(node2);
		//		nodeStack.push(node3);
		//		nodeSet.add(node1);
		//		nodeSet.add(node2);
		//		nodeSet.add(node3);
		//		Node currNode = nodeStack.pop();
		//		currNode.data = 10;
		//		for (Node eachNode : nodeSet) {
		//			System.out.println(eachNode.data);
		//		}

		//		node1.addNeighbor(2, 10);
		//		node1.addNeighbor(3, 2);
		//		node2.addNeighbor(1, 10);
		//		node2.addNeighbor(3, 10);
		//		node3.addNeighbor(1, 2);
		//		node3.addNeighbor(2, 10);
		//		while (node1.hasMinNeighbor()) {
		//			System.out.println("Neighbors: ");
		//			System.out.print(node1.getMinNeighbor().vertexNum + " ");
		//		}

		//		PriorityQueue<Integer> pq1z = new PriorityQueue<Integer>();
		//		PriorityQueue<Integer> pq2z = new PriorityQueue<Integer>();
		//		pq1z.add(1);
		//		pq1z.add(2);
		//		pq2z.add(3);
		//		pq2z.add(4);
		//		pq1z = pq2z;
		//		System.out.println(pq1z.remove());

		//		CircleQueue aCQ = new CircleQueue();
		//		while (aCQ.hasNext()) {
		//			System.out.println(aCQ.getNext());
		//		}
		TreeMap<Integer, Integer> aTM = new TreeMap<Integer, Integer>();
		aTM.put(1, 1);
		aTM.put(2, 2);
		aTM.remove(1);
		for (Map.Entry<Integer, Integer> aEn : aTM.entrySet()) {
			System.out.println(aEn.getKey());
		}
	}

	static class CircleQueue {
		PriorityQueue<Integer> pq1;
		PriorityQueue<Integer> pq2;

		public CircleQueue() {
			pq1 = new PriorityQueue<Integer>();
			pq2 = new PriorityQueue<Integer>();
			pq1.add(1);
			pq1.add(2);
			pq1.add(3);
		}

		public int getNext() {
			if (pq1.size() == 0) {
				pq1 = pq2;
			}
			int removedInt = pq1.remove();
			pq2.add(removedInt);
			return removedInt;
		}

		public boolean hasNext() {
			if (pq1.size() == 0) {
				pq1 = pq2;
				pq2 = new PriorityQueue<Integer>();
			}
			return (pq1.size() != 0);
		}
	}

	//	static class Node implements Comparable {
	//		int data;
	//
	//		public Node(int pData) {
	//			data = pData;
	//		}
	//
	//		public void printHello() {
	//			System.out.println("Hello");
	//		}
	//
	//		@Override
	//		public int compareTo(Object o) {
	//			// TODO Auto-generated method stub
	//			Node compNode = (Node) o;
	//			return this.data - compNode.data;
	//		}
	//	}
}
