import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class CopyMainClass {

	static final int DIST_LIMIT = 6;
	// Store all nodes in HashMap for quickly access all nodes
	static HashMap<Integer, Node> allNodesHM;
	// Keep track of all edges, key is the weight of edge, value is HashSet of Pair(vNum1,vNum2)
	static TreeMap<Integer, HashSet<Pair>> weightEdgeTreeMap;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		getInput();
		printTreeMap();
		start();
	}

	/**
	 * Get the input from the file and do the following: 1. Put all the weights of the edge in AVL tree 2. Store all
	 * nodes in HashMap for quickly access
	 * 
	 * @throws FileNotFoundException
	 */
	public static void getInput() throws FileNotFoundException {
		weightEdgeTreeMap = new TreeMap<Integer, HashSet<Pair>>();
		allNodesHM = new HashMap<Integer, Node>();
		FileReader aFR = new FileReader("graph2.txt");
		Scanner sc = new Scanner(aFR);
		Scanner tempSc, tempSc2;
		String eachLine, eachLineNode;
		int vertexNum, neighborNum, weight;
		Node newNode;
		Pair newPair;

		while (sc.hasNextLine()) {
			eachLine = sc.nextLine();
			tempSc = new Scanner(eachLine);
			tempSc.useDelimiter(":");
			vertexNum = Integer.parseInt(tempSc.next());
			// Initializing new Node from the input file
			newNode = new Node(vertexNum);

			eachLine = tempSc.next();
			tempSc = new Scanner(eachLine);
			tempSc.useDelimiter("]");
			while (tempSc.hasNext()) {
				eachLineNode = tempSc.next();
				tempSc2 = new Scanner(eachLineNode.replaceAll("\\[|\\]", ""));
				tempSc2.useDelimiter(",");
				neighborNum = Integer.parseInt(tempSc2.next());
				weight = Integer.parseInt(tempSc2.next());

				newNode.addNeighbor(neighborNum, weight);
				HashSet<Pair> aHS = weightEdgeTreeMap.get(weight);
				if (aHS == null) {
					aHS = new HashSet<Pair>();
				}
				newPair = new Pair(vertexNum, neighborNum);
				aHS.add(newPair);
				weightEdgeTreeMap.put(weight, aHS);
				// Store all nodes in HashMap
				allNodesHM.put(vertexNum, newNode);
			}
		}
		sc.close();
	}

	public static void start() {
		// Get the set from each key in TreeMap that maps to weight of edge
		HashSet<Pair> minPairSet = weightEdgeTreeMap.firstEntry().getValue();
		Node currentNode = null, currNeighbor, lastVisitedNode = null;
		Node.Pair neighborPair;
		// Keep track of the Vertex that covers the most vertices
		BestPair theBestPair = null;
		int currentWeight = 0, maxVerticesCovered = 0;
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> weightStack = new Stack<Integer>();
		// Keep track of how many vertices the selected vertex cover - want to maximum the vertices covered
		HashSet<Integer> coveredElements = new HashSet<Integer>();

		//while (weightEdgeTreeMap.size() > 0) {
		// Get the HashSet from the TreeMap that maps that weight of Edge
		if (minPairSet != null && minPairSet.size() > 0) {
			// For each vertexNum in the selected edge weight
			for (Integer eachVertexNum : minPairSet) {
				currentNode = allNodesHM.get(eachVertexNum);

				System.out.println("CurrentNode: " + eachVertexNum);

				// Add currentNode to Set
				coveredElements.add(eachVertexNum);
				nodeStack.push(currentNode);
				currentNode.visited = true;

				while (!nodeStack.isEmpty()) {
					currentNode = nodeStack.peek();

					// While there is a uncovered neighbor
					while (currentNode.hasMinNeighbor()) {
						neighborPair = currentNode.getMinNeighbor();
						currNeighbor = allNodesHM.get(neighborPair.vertexNum);

						if (currNeighbor.visited) {
							continue;
						} else {
							if ((currentWeight + neighborPair.weightValue) > DIST_LIMIT) {
								continue;
							} else {
								weightStack.push(neighborPair.weightValue);
								currentWeight += neighborPair.weightValue;
								currNeighbor.visited = true;
								nodeStack.push(currNeighbor);
								coveredElements.add(neighborPair.vertexNum);
								currentNode = currNeighbor;
							}
						}
					}
					currentNode.visited = false;
					lastVisitedNode = nodeStack.pop();
					// when there is no more minNeighbor, reset priorityQueue
					lastVisitedNode.resetPQ();
					currentWeight -= (weightStack.isEmpty()) ? 0 : weightStack.pop();
				}

				System.out.println("Printing Set: ");
				for (Integer eachInt : coveredElements) {
					System.out.print(eachInt + " ");
				}
				System.out.println();

				if (coveredElements.size() > maxVerticesCovered) {
					maxVerticesCovered = coveredElements.size();
					theBestPair = new BestPair(lastVisitedNode.vertexNum, coveredElements.size());
					// Reset the HashSet that stores covered vertices
				}
				if (maxVerticesCovered == (DIST_LIMIT - 1)) {
					break;
				}
				coveredElements = new HashSet<Integer>();
			}
		}
		// Put the gas station on the best vertex

		System.out.println("Best Pair");
		System.out.println(theBestPair.vertexNum + " " + theBestPair.verticesCovered);
	}

	public static void newStart() {
		// Get the HashSet of Pairs with minimum edge
		HashSet<Pair> minPairSet = weightEdgeTreeMap.firstEntry().getValue();
		// For each vertexNum in Pairs, do not want to go thru certain element twice
		HashSet<Integer> eachNumInPair = new HashSet<Integer>();
		// Keep track of how many vertices the selected vertex covers - want to maximize the vertices covered
		HashSet<Integer> coveredElements = new HashSet<Integer>();
		// Current starting vertexNum
		int eachVertexNum = 0;
		Node currentNode = null, currentNeighbor, lastVisitedNode = null;
		Node.Pair neighborPair;
		BestPair theBestPair = null;
		int currentWeight = 0, maxVerticesCovered = 0;
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> weightStack = new Stack<Integer>();

		// For each pair in the HashSet
		for (Pair eachMinPair : minPairSet) {
			// Get each int from Pair
			eachVertexNum = eachMinPair.getEntry();
			// When eachVertexNum == -1 is when we got two ints already, so stop
			while (eachVertexNum != -1) {
				// Check to make sure we don't run thru element twice by putting
				//  the starting element in the HashSet, duplicate value will be ignore
				if (eachNumInPair.add(eachVertexNum)) {
					currentNode = allNodesHM.get(eachVertexNum);
					coveredElements.add(eachVertexNum);
					nodeStack.push(currentNode);
					currentNode.visited = true;

					while (!nodeStack.isEmpty()) {
						currentNode = nodeStack.peek();

						while (currentNode.hasMinNeighbor()) {
							neighborPair = currentNode.getMinNeighbor();
							currentNeighbor = allNodesHM.get(neighborPair.vertexNum);

							if (currentNeighbor.visited) {
								continue;
							} else {
								if ((currentWeight + neighborPair.weightValue) > DIST_LIMIT) {
									continue;
								} else {
									weightStack.push(neighborPair.weightValue);
									currentWeight += neighborPair.weightValue;
									currentNeighbor.visited = true;
									nodeStack.push(currentNeighbor);
									coveredElements.add(neighborPair.vertexNum);
									currentNode = currentNeighbor;
								}
							}
						}
						currentNode.visited = false;
						lastVisitedNode = nodeStack.pop();
						lastVisitedNode.resetPQ();
						currentWeight -= (weightStack.isEmpty()) ? 0 : weightStack.pop();
					}
					if (coveredElements.size() > maxVerticesCovered) {
						maxVerticesCovered = coveredElements.size();
						theBestPair = new BestPair(lastVisitedNode.vertexNum, coveredElements.size());
					}
					coveredElements = new HashSet<Integer>();
				}
				eachVertexNum = eachMinPair.getEntry();
			}
		}
		System.out.println("Best Pair");
		System.out.println(theBestPair.vertexNum + " " + theBestPair.verticesCovered);
	}

	public static void putGasStation(int pBestVertexNum) {
		Node currentNode, currNeighbor;
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> weightStack = new Stack<Integer>();
		Node.Pair neighborPair = null;
		int currentWeight = 0;

		currentNode = allNodesHM.get(pBestVertexNum);

		System.out.println("CurrentNode: " + pBestVertexNum);

		nodeStack.push(currentNode);
		currentNode.visited = true;

		while (!nodeStack.isEmpty()) {
			currentNode = nodeStack.peek();

			// While there is a uncovered neighbor
			while (currentNode.hasMinNeighbor()) {
				neighborPair = currentNode.getMinNeighbor();
				currNeighbor = allNodesHM.get(neighborPair.vertexNum);

				if (currNeighbor.visited) {
					continue;
				} else {
					if ((currentWeight + neighborPair.weightValue) > DIST_LIMIT) {
						continue;
					} else {
						weightStack.push(neighborPair.weightValue);
						currentWeight += neighborPair.weightValue;
						removeEdgeFromTreeMap(neighborPair.weightValue, currNeighbor.vertexNum);
						currNeighbor.visited = true;
						nodeStack.push(currNeighbor);
						currentNode = currNeighbor;
					}
				}
			}
			currentNode.visited = false;
			nodeStack.pop();
			currentWeight -= (weightStack.isEmpty()) ? 0 : weightStack.pop();
		}
	}

	public static void removeEdgeFromTreeMap(int pWeight, int pVertexNum) {
		HashSet<Integer> elementSet = weightEdgeTreeMap.get(pWeight);
		elementSet.remove(pVertexNum);
	}

	public static void printAllNodesAndNeighbors() {
		for (Map.Entry<Integer, HashSet<Integer>> eachEle : weightEdgeTreeMap.entrySet()) {
			HashSet<Integer> aHS = eachEle.getValue();
			for (Integer eachInt : aHS) {
				Node eachNode = allNodesHM.get(eachInt);
				System.out.printf("VertexNum: %d with neighbors: ", eachInt);
				while (eachNode.hasMinNeighbor()) {
					System.out.print(eachNode.getMinNeighbor().vertexNum + " ");
				}
				eachNode.resetPQ();
				System.out.println();
			}
			System.out.println();
		}
	}

	public static void printTreeMap() {
		for (Map.Entry<Integer, HashSet<Integer>> eachEntry : weightEdgeTreeMap.entrySet()) {
			System.out.print(eachEntry.getKey() + " : ");
			HashSet<Integer> aHS = eachEntry.getValue();
			for (Integer eachInt : aHS) {
				System.out.print(eachInt + " ");
			}
			System.out.println();
		}
	}

	public static void newStart() {
		// Get the HashSet of Pairs with minimum edge
		HashSet<Pair> minPairSet = weightEdgeTreeMap.firstEntry().getValue();
		// For each vertexNum in Pairs, do not want to go thru certain element twice
		HashSet<Integer> eachNumInPair = new HashSet<Integer>();
		// Keep track of how many vertices the selected vertex covers - want to maximize the vertices covered
		HashSet<Integer> coveredElements = new HashSet<Integer>();
		// Current starting vertexNum
		int eachVertexNum = 0;
		Node currentNode = null, currentNeighbor, lastVisitedNode = null;
		Node.Pair neighborPair;
		BestPair theBestPair = null;
		int currentWeight = 0, maxVerticesCovered = 0;
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> weightStack = new Stack<Integer>();

		// For each pair in the HashSet
		for (Pair eachMinPair : minPairSet) {
			// Get each int from Pair
			eachVertexNum = eachMinPair.getEntry();
			// When eachVertexNum == -1 is when we got two ints already, so stop
			while (eachVertexNum != -1) {
				// Check to make sure we don't run thru element twice by putting
				//  the starting element in the HashSet, duplicate value will be ignore
				if (eachNumInPair.add(eachVertexNum)) {
					currentNode = allNodesHM.get(eachVertexNum);
					coveredElements.add(eachVertexNum);
					nodeStack.push(currentNode);
					currentNode.visited = true;

					while (!nodeStack.isEmpty()) {
						currentNode = nodeStack.peek();

						while (currentNode.hasMinNeighbor()) {
							neighborPair = currentNode.getMinNeighbor();
							currentNeighbor = allNodesHM.get(neighborPair.vertexNum);

							if (currentNeighbor.visited) {
								continue;
							} else {
								if ((currentWeight + neighborPair.weightValue) > DIST_LIMIT) {
									continue;
								} else {
									weightStack.push(neighborPair.weightValue);
									currentWeight += neighborPair.weightValue;
									currentNeighbor.visited = true;
									nodeStack.push(currentNeighbor);
									coveredElements.add(neighborPair.vertexNum);
									currentNode = currentNeighbor;
								}
							}
						}
						currentNode.visited = false;
						lastVisitedNode = nodeStack.pop();
						lastVisitedNode.resetPQ();
						currentWeight -= (weightStack.isEmpty()) ? 0 : weightStack.pop();
					}
					if (coveredElements.size() > maxVerticesCovered) {
						maxVerticesCovered = coveredElements.size();
						theBestPair = new BestPair(lastVisitedNode.vertexNum, coveredElements.size());
					}
					coveredElements = new HashSet<Integer>();
				}
				eachVertexNum = eachMinPair.getEntry();
			}
		}
		System.out.println("Best Pair");
		System.out.println(theBestPair.vertexNum + " " + theBestPair.verticesCovered);
	}

	static class BestPair {
		int vertexNum;
		int verticesCovered;

		public BestPair(int pVertexNum, int pVerticesCovered) {
			vertexNum = pVertexNum;
			verticesCovered = pVerticesCovered;
		}
	}

	static class Pair {
		int vertexNum1;
		int vertexNum2;
		byte count;

		public Pair(int pNum1, int pNum2) {
			vertexNum1 = pNum1;
			vertexNum2 = pNum2;
			count = 0;
		}

		public int getEntry() {
			if (count == 0) {
				count++;
				return vertexNum1;
			} else if (count == 1) {
				count++;
				return vertexNum2;
			}
			return -1;
		}

		public boolean equals(Object obj) {
			Pair comPair = (Pair) obj;
			boolean bool1 = (this.vertexNum1 == comPair.vertexNum1 && this.vertexNum2 == comPair.vertexNum2);
			boolean bool2 = (this.vertexNum1 == comPair.vertexNum2 && this.vertexNum2 == comPair.vertexNum1);
			return (bool1 || bool2);
		}
	}
}
