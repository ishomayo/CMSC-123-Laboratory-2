package lab2;

import java.util.ArrayList;

// TBA: path route, use ArrayList<Integer>
public class DynamicProgrammingSinglePath {
	private static final int N = 6;
	private static ArrayList<Integer>[][] storedPath;
	private static ArrayList<Integer> currentPath;
	private static int completeVisited, startNode;
	private static int[][] adjacencyMatrix; // do i need to explain this lol
	private static int[][] storedCost; // memoization
	
	// dynamic programming func
	private static int DynamicProgramming(int visited, int currentNode) {
		// if storedCost[visited][currentNode] isn't empty (exists):
		if(storedCost[visited][currentNode] != 0) {
			currentPath.addAll(storedPath[visited][currentNode]); 	// set currentPath as existing storedPath()
			return storedCost[visited][currentNode]; 				// return the value of storedCost
		}
				
		// if we visited all cities (except 0):
		if (visited == 0) {
			currentPath.add(startNode);		// add 0 to currentPath
			currentPath.add(currentNode);  	// add currentNode to currentPath
			return adjacencyMatrix[currentNode][startNode];  // return the distance between 0 and currentNode
		}
		
		int minCost = 0; // minimal cost
		for(int nextNode = 1 ; nextNode < N; nextNode++) {
			currentPath.clear(); 	// clear currentPath for every iteration
			
			if ((visited & (1 << nextNode)) != 0 && adjacencyMatrix[currentNode][nextNode] != 0) {
				// get the cost between currentNode and nextNode + all the other previous nodes
				int cost = adjacencyMatrix[currentNode][nextNode] + DynamicProgramming(visited - (1 << nextNode), nextNode);
				currentPath.add(currentNode); //
				
				minCost = (minCost != 0) ? Math.min(minCost, cost) : cost;	// if minCost != 0, set it to the minimum between minCost and cost (otherwise, just set it to cost)
					
				if(minCost == cost) { 
					storedPath[visited][currentNode].clear();
					storedPath[visited][currentNode].addAll(currentPath);
				}
			}
		}
		
		// add current node to the storedPath and set currentPath = storedPath		
		currentPath.clear();
		currentPath.addAll(storedPath[visited][currentNode]);
		
		// save minCost in storedCost[][] memoization
		storedCost[visited][currentNode] = minCost;
		return minCost;
	}
	
	// set matrix 
	private static int[][] setMatrix() {
		int[][] matrix = { 
				{0, 9, 7, 3, 12, 11}, 
				{9, 0, 8, 5, 14, 13},
				{7, 8, 0, 6, 10, 15},
				{3, 5, 6, 0, 9,  7},
				{12, 14, 10, 9, 0,  4},
				{11, 13, 15, 7, 4,  0},
		}; 
		return matrix;
	}
	
	// main function
	public static void main(String[] args) {
		storedPath = new ArrayList[1 << N][N];
		currentPath = new ArrayList<Integer>();
		storedCost = new int[1 << N][N]; // set memoization
		adjacencyMatrix = setMatrix(); // set adjacencyMatrix
		
		// fill all storedCost[][] with 0 (meaning no distance) and init storedPath
		for(int i = 0; i < (1 << N); i++) { 
			for(int j = 0; j < N; j++) {
				storedCost[i][j] = 0;
				storedPath[i][j] = new ArrayList<Integer>();
			}
		}
		
		// start from all nodes visited & set startNode = 0
		completeVisited = (1 << N) - 1; 
		startNode = 0;
		int minCost = DynamicProgramming(completeVisited - 1, startNode);
		
		// print minCost and current Path
		System.out.println("Smallest Path Cost: " + minCost);
		System.out.println("Path Route: " + currentPath);
	}	
	
}
