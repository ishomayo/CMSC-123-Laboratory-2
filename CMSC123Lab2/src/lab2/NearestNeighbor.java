package lab2;

import java.util.Arrays;
import java.util.Scanner;

public class NearestNeighbor {
    static final int MAX_DIST = Integer.MAX_VALUE; // for distance comparisons
    
    // nodes and weight matrix
    static final int[][] matrix = {
        {  0,  8, 16, 12, 10 },
        {  8,  0, 14, 11,  3 },
        { 16, 14,  0,  5, 19 },
        { 12, 11,  5,  0, 21 },
        { 10,  3, 19, 21,  0 },
    };
    
    static final String[] nodes = {"A", "B", "C", "D", "E"};
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = matrix.length;
        boolean[] visited = new boolean[n]; // keeps track of visited nodes
        int[] path = new int[n + 1]; // stores the sequence of visited nodes
        int totalCost = 0; // keeps track of the total travel

        // starting node input
        int current = -1; // initialize current node as invalid to ensure input is valid
        while (current == -1) {
            System.out.print("Enter starting node (" + String.join(", ", nodes) + "): ");
            String startNode = scanner.next().toUpperCase(); // case flexibility
            current = Arrays.asList(nodes).indexOf(startNode); // find the index of the node in the array
            if (current == -1) {
                System.out.println("Enter a valid node.");
            }
        }
        
        System.out.println("Greedy selection:");
        System.out.print(nodes[current]); // print the starting node
        visited[current] = true; // mark the starting node as visited
        path[0] = current; // store the starting node in the path
        
        // finding the earest neighbor and building a path
        for (int i = 1; i < n; i++) {
            int nearest = -1; // holds the nearest node index
            int minDist = MAX_DIST; // start with the maximum possible distance
            
            for (int j = 0; j < n; j++) { // loop through all nodes to find the nearest unvisited node.
                if (!visited[j] && matrix[current][j] < minDist) { // if not visited and has a smaller distance.
                    nearest = j; // update nearest node.
                    minDist = matrix[current][j]; // update minimum distance.
                }
            }
            
            if (nearest != -1) { // valid nearest node is found.
                System.out.print(" -> " + nodes[nearest] + "(" + minDist + ")"); // print the step
                totalCost += minDist; // add distance to total
                visited[nearest] = true; // mark the node as visited
                path[i] = nearest; // store the node in the path
                current = nearest; // move to the next node
            }
        }
        
        // completing the cycle by returning to the starting node
        totalCost += matrix[current][path[0]]; // add cost to return to the start
        System.out.println(" -> " + nodes[path[0]] + "(" + matrix[current][path[0]] + ")"); // print final step
        path[n] = path[0]; // store the start node again to complete the cycle
        
        // displaying complete path
        System.out.println("\nFinal Path: " + getFinalPath(path));
        System.out.println("Total Distance: " + totalCost); 
        scanner.close();
    }
    
    // formatting the path as a readable string
    private static String getFinalPath(int[] path) {
        StringBuilder result = new StringBuilder("["); // start with an opening bracket
        for (int i = 0; i < path.length; i++) { // loop through the path array
            result.append(nodes[path[i]]); // append the node name
            if (i < path.length - 1) result.append(", "); // add comma unless it's the last element
        }
        result.append("]"); // close the bracket
        return result.toString(); // return formatted path
    }
}