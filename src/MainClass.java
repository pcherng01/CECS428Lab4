import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class MainClass {

	static final int DIST_LIMIT = 30;
	// Store all nodes in HashMap for quickly access all nodes
	static HashMap<Integer, Node> allNodesHM;
	// Keep track of all edges, key is the weight of edge, value is HashSet of Pair(vNum1,vNum2)
	static TreeMap<Integer, HashSet<Pair>> weightEdgeTreeMap;
	static HashSet<Integer> gasStations;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		getInput();
		PrintWriter pw = new PrintWriter("output.txt");
		while (weightEdgeTreeMap.firstEntry() != null) {
			//printTreeMap();
			System.out.println(weightEdgeTreeMap.size());
			System.out.println("Num of gas stations: " + gasStations.size());
			newStart();
		}
		System.out.println("Num of gas stations: " + gasStations.size());
		for (Integer anInt : gasStations) {
			pw.print(anInt + "x");
		}
	}

	/**
	 * Read from input file and put every Nodes in allNodesHM Put the weightedEdge in TreeMap that maps to Pair of
	 * vertices with that weigted edge
	 * 
	 * @throws FileNotFoundException
	 */
	public static void getInput() throws FileNotFoundException {
		gasStations = new HashSet<Integer>();
		weightEdgeTreeMap = new TreeMap<Integer, HashSet<Pair>>();
		allNodesHM = new HashMap<Integer, Node>();
		FileReader aFR = new FileReader("graphz.txt");
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
			// System.out.println(vertexNum);
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

		if (weightEdgeTreeMap.firstEntry().getKey() > DIST_LIMIT) {
			for (Pair eachMinPair : minPairSet) {
				eachVertexNum = eachMinPair.getEntry();
				while (eachVertexNum != -1) {
					if (eachNumInPair.add(eachVertexNum)) {
						currentNode = allNodesHM.get(eachVertexNum);
						if (!currentNode.visited) {
							System.out.printf("Puttingz Gas Station on the best Vertex %d\n\n", currentNode.vertexNum);
							if (weightEdgeTreeMap.firstEntry() != null)
								removeEdgeFromTreeMap(weightEdgeTreeMap.firstEntry().getKey(), eachMinPair);
							gasStations.add(currentNode.vertexNum);
						}
					}
					eachVertexNum = eachMinPair.getEntry();
				}
				// Prevent concurrent Modification Exception
				break;
			}
		} else {
			// For each pair in the HashSet
			for (Pair eachMinPair : minPairSet) {
				eachMinPair.resetEntry();

				// Get each int from Pair
				eachVertexNum = eachMinPair.getEntry();

				// When eachVertexNum == -1 is when we got two ints already, so stop
				while (eachVertexNum != -1) {
					// Check to make sure we don't run thru element twice by putting
					//  the starting element in the HashSet, duplicate value will be ignore
					if (eachNumInPair.add(eachVertexNum)) {
						currentNode = allNodesHM.get(eachVertexNum);

						currentNode.resetPQ();

						coveredElements.add(eachVertexNum);
						nodeStack.push(currentNode);
						currentNode.visited = true;

						while (!nodeStack.isEmpty()) {
							currentNode = nodeStack.peek();

							while (currentNode.hasMinNeighbor()) {
								neighborPair = currentNode.getMinNeighbor();
								currentNeighbor = allNodesHM.get(neighborPair.vertexNum);

								//							System.out.printf("CurrNode: %d, neighbor: %d\n", currentNode.vertexNum,
								//									currentNeighbor.vertexNum);

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

										//										System.out.printf("CurrentNode: %d with currentWeight: %d\n", eachVertexNum,
										//												currentWeight);
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
							System.out.printf("Best pair: %d, %d\n", lastVisitedNode.vertexNum, coveredElements.size());
						}
						coveredElements = new HashSet<Integer>();
					}
					eachVertexNum = eachMinPair.getEntry();
				}
				break;
			}
			System.out.println("Best Pair");
			System.out.println(theBestPair.vertexNum + " " + theBestPair.verticesCovered);
			System.out.printf("Putting Gas Station on the best Vertex %d\n\n", theBestPair.vertexNum);
			putGasStation(theBestPair.vertexNum);
		}
	}

	public static void putGasStation(int pBestVertexNum) {
		gasStations.add(pBestVertexNum);
		HashMap<Integer, HashSet<Pair>> removedPairHM = new HashMap<Integer, HashSet<Pair>>();
		Node currentNode, currNeighbor;
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> weightStack = new Stack<Integer>();
		Node.Pair neighborPair = null;
		int currentWeight = 0;

		currentNode = allNodesHM.get(pBestVertexNum);

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
						HashSet<Pair> removedPairHS = removedPairHM.get(neighborPair.weightValue);
						if (removedPairHS == null) {
							removedPairHS = new HashSet<Pair>();
						}
						removedPairHS.add(new Pair(currentNode.vertexNum, currNeighbor.vertexNum));
						removedPairHM.put(neighborPair.weightValue, removedPairHS);
						weightStack.push(neighborPair.weightValue);
						currentWeight += neighborPair.weightValue;
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
		// Removing the pair from the TreeMap
		for (Map.Entry<Integer, HashSet<Pair>> eachEntry : removedPairHM.entrySet()) {
			for (Pair eachPair : eachEntry.getValue()) {
				removeEdgeFromTreeMap(eachEntry.getKey(), eachPair);
			}
		}
	}

	public static void removeEdgeFromTreeMap(int pWeight, Pair pRemovePair) {
		HashSet<Pair> elementSet = weightEdgeTreeMap.get(pWeight);
		if (elementSet != null && elementSet.size() != 0) {
			elementSet.remove(pRemovePair);
		}
		if (elementSet != null && elementSet.size() == 0) {
			weightEdgeTreeMap.remove(pWeight);
		}
	}

	public static void printAllNodesAndNeighbors() {
		//		for (Map.Entry<Integer, HashSet<Integer>> eachEle : weightEdgeTreeMap.entrySet()) {
		//			HashSet<Integer> aHS = eachEle.getValue();
		//			for (Integer eachInt : aHS) {
		//				Node eachNode = allNodesHM.get(eachInt);
		//				System.out.printf("VertexNum: %d with neighbors: ", eachInt);
		//				while (eachNode.hasMinNeighbor()) {
		//					System.out.print(eachNode.getMinNeighbor().vertexNum + " ");
		//				}
		//				eachNode.resetPQ();
		//				System.out.println();
		//			}
		//			System.out.println();
		//		}
	}

	public static void printTreeMap() {
		for (Map.Entry<Integer, HashSet<Pair>> eachEntry : weightEdgeTreeMap.entrySet()) {
			System.out.print(eachEntry.getKey() + " : ");
			HashSet<Pair> aHS = eachEntry.getValue();
			for (Pair eachPair : aHS) {
				System.out.printf("(%d,%d)", eachPair.vertexNum1, eachPair.vertexNum2);
			}
			System.out.println();
		}
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

		public void resetEntry() {
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

		public boolean equals(Object o) {
			Pair comPair = (Pair) o;
			boolean bool1 = (this.vertexNum1 == comPair.vertexNum1 && this.vertexNum2 == comPair.vertexNum2);
			boolean bool2 = (this.vertexNum1 == comPair.vertexNum2 && this.vertexNum2 == comPair.vertexNum1);
			return (bool1 || bool2);
		}

		public int hashCode() {
			return (new Integer(vertexNum1).hashCode()) + (new Integer(vertexNum2).hashCode());
		}
	}
}
