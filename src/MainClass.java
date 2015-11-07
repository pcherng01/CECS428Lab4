import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MainClass {

	// Store all nodes in HashMap for quickly access all nodes
	static HashMap<Integer, LabNode> allNodesHM;
	// Keep track of all weights of all edges
	static AVLTree<Integer> tree;
	// Keep track of 
	static HashMap<Integer, HashMap<Integer, Integer>> weightsAndVerticesHM;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		getInput();
	}

	/**
	 * Get the input from the file and do the following: 1. Put all the weights of the edge in AVL tree 2. Store all
	 * nodes in HashMap for quickly access
	 * 
	 * @throws FileNotFoundException
	 */
	public static void getInput() throws FileNotFoundException {
		weightsAndVerticesHM = new HashMap<Integer, HashMap<Integer, Integer>>();
		Set<Integer> aSet = new HashSet<Integer>();
		tree = new AVLTree<Integer>();
		allNodesHM = new HashMap<Integer, LabNode>();
		FileReader aFR = new FileReader("graphz.txt");
		Scanner sc = new Scanner(aFR);
		Scanner tempSc, tempSc2;
		String eachLine, eachLineNode;
		int vertexNum, neighborNum, weight;
		LabNode newNode;

		while (sc.hasNextLine()) {
			eachLine = sc.nextLine();
			tempSc = new Scanner(eachLine);
			tempSc.useDelimiter(":");
			vertexNum = Integer.parseInt(tempSc.next());
			// Initializing new Node from the input file
			newNode = new LabNode(vertexNum);
			eachLine = tempSc.next();
			tempSc = new Scanner(eachLine);
			tempSc.useDelimiter("]");
			while (tempSc.hasNext()) {
				eachLineNode = tempSc.next();
				tempSc2 = new Scanner(eachLineNode.replaceAll("\\[|\\]", ""));
				tempSc2.useDelimiter(",");
				neighborNum = Integer.parseInt(tempSc2.next());
				weight = Integer.parseInt(tempSc2.next());
				// Check for duplicate before adding in the AVL tree
				if (aSet.add(weight)) {
					tree.insert(new Integer(weight));
				}
				addWeightAndVertex(weight, vertexNum);
				newNode.addNeighbor(neighborNum, weight);
				// Store all nodes in HashMap
				allNodesHM.put(vertexNum, newNode);
			}
		}

		//		System.out.println("Printing tree");
		//		tree.PrintTree();
		printWeightsAndVerticesHM();
		sc.close();
	}

	public static void addWeightAndVertex(int pWeighOfEdge, int pVertexNum) {
		// Get the HashMap of the weightedEdge
		HashMap<Integer, Integer> aHM = weightsAndVerticesHM.get(pWeighOfEdge);
		// If null, initialize it
		if (aHM == null) {
			aHM = new HashMap<Integer, Integer>();
		}
		// Store the Vertex in the HashMap
		aHM.put(pVertexNum, pVertexNum);
		weightsAndVerticesHM.put(pWeighOfEdge, aHM);
	}

	public static void printWeightsAndVerticesHM() {
		for (Map.Entry<Integer, HashMap<Integer, Integer>> eachHM : weightsAndVerticesHM.entrySet()) {
			System.out.println(eachHM.getKey() + ": " + eachHM.getValue().size());
		}
	}
}
