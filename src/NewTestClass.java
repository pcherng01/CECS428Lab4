import java.util.HashSet;

public class NewTestClass {
	public static void main(String[] args) {
		HashSet<Node> nodeSet = new HashSet<Node>();
		nodeSet.add(new Node(1));
		nodeSet.add(new Node(2));
		nodeSet.add(new Node(2));

	}

	static class Node {
		int data;

		public boolean equals(Object o) {
			Node compNode = (Node) o;
			if (compNode.data == data) {
				return true;
			} else
				return false;
		}

		public Node(int pData) {
			data = pData;
		}
	}
}
