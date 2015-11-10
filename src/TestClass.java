import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

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
		//		TreeMap<Integer, Integer> aTM = new TreeMap<Integer, Integer>();
		//		aTM.put(1, 1);
		//		aTM.put(2, 2);
		//		aTM.remove(1);
		//		for (Map.Entry<Integer, Integer> aEn : aTM.entrySet()) {
		//			System.out.println(aEn.getKey());
		//		}

		printTreeMap();
		//		PrintWriter pw = new PrintWriter("outputz.txt");
		//		System.out.print("Size of allNodes: " + allNodesHM.size());
		//		System.out.print("\nSize of requiredVertices: " + requiredVertices.size());
		//		while (coveredVerticesHS.size() != allNodesHM.size()) {
		//			if (allNodesHM.size() - coveredVerticesHS.size() < 3) {
		//				System.out.println("YOLO");
		//				for (Map.Entry<Integer, Node> eachNode : allNodesHM.entrySet()) {
		//					if (!coveredVerticesHS.contains(eachNode.getKey())) {
		//						coveredVerticesHS.add(eachNode.getKey());
		//						gasStations.add(eachNode.getKey());
		//					}
		//				}
		//			}
		//			System.out.println("Total Vertices: " + allNodesHM.size());
		//			System.out.println("Vertices covered so far: " + coveredVerticesHS.size());
		//			System.out.println("Num of gas stations: " + gasStations.size());
		//			newStart();
		//		}
		//		System.out.println("Num of gas stations: " + gasStations.size());
		//		for (Integer anInt : gasStations) {
		//			pw.print(anInt + "x");
		//		}
		//		pw.close();
	}

	public static void newStart() {
		int newVerticesCovered = 0;
		// Get the HashSet of Pairs with minimum edge
		HashSet<Pair> minPairSet = weightEdgeTreeMap.firstEntry().getValue();
		// For each vertexNum in Pairs, do not want to go thru certain element twice
		HashSet<Integer> eachNumInPairHS = new HashSet<Integer>();
		// Keep track of how many vertices the selected vertex covers - want to maximize the vertices covered
		HashSet<Integer> coveredElementsHS = new HashSet<Integer>();
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
					if (eachNumInPairHS.add(eachVertexNum)) {
						currentNode = allNodesHM.get(eachVertexNum);
						if (!currentNode.visited) {
							System.out.printf("Puttingz Gas Station on the best Vertex %d\n\n", currentNode.vertexNum);
							if (weightEdgeTreeMap.firstEntry() != null)
								removeEdgeFromTreeMap(weightEdgeTreeMap.firstEntry().getKey(), eachMinPair);
							gasStations.add(currentNode.vertexNum);
							coveredVerticesHS.add(currentNode.vertexNum);
						}
					}
					eachVertexNum = eachMinPair.getEntry();
				}
				// Prevent concurrent Modification Exception
				break;
			}
		} else {
			// For each pair in the HashSet of the minimum weighted edge
			for (Pair eachMinPair : minPairSet) {
				eachMinPair.resetEntry();

				// Get each int from Pair
				eachVertexNum = eachMinPair.getEntry();

				// When eachVertexNum == -1 is when we got two ints already, so stop
				while (eachVertexNum != -1) {
					// Check to make sure we don't run thru element twice by putting
					//  the starting element in the HashSet, duplicate value will be ignore
					if (eachNumInPairHS.add(eachVertexNum)) {
						currentNode = allNodesHM.get(eachVertexNum);

						currentNode.resetPQ();

						//coveredElements.add(eachVertexNum); // Might not have to add starting vertex
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

										Pair removedPair = new Pair(currentNode.vertexNum, currentNeighbor.vertexNum);
										removeEdgeFromTreeMap(neighborPair.weightValue, removedPair);
										if (coveredVerticesHS.add(neighborPair.vertexNum))
											newVerticesCovered++;

										weightStack.push(neighborPair.weightValue);
										currentWeight += neighborPair.weightValue;
										currentNeighbor.visited = true;
										nodeStack.push(currentNeighbor);
										coveredElementsHS.add(neighborPair.vertexNum);
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

						if (coveredElementsHS.size() > maxVerticesCovered) {
							maxVerticesCovered = coveredElementsHS.size();
							theBestPair = new BestPair(lastVisitedNode.vertexNum, coveredElementsHS.size());
							System.out.printf("Best pair: %d, %d\n", lastVisitedNode.vertexNum,
									coveredElementsHS.size());
						}
						if (newVerticesCovered > 0) {
							gasStations.add(lastVisitedNode.vertexNum);
							coveredVerticesHS.add(lastVisitedNode.vertexNum);
						}
						coveredElementsHS = new HashSet<Integer>();
					}
					eachVertexNum = eachMinPair.getEntry();
					break; // Remove if want to check between these two vertices in Pair
				}
				break; // Remove if want to check the best 
			}
			//			System.out.println("Best Pair");
			//			System.out.println(theBestPair.vertexNum + " " + theBestPair.verticesCovered);
			System.out.printf("Putting Gas Station on the best Vertex %d\n\n", theBestPair.vertexNum);
			//putGasStation(theBestPair.vertexNum);
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
