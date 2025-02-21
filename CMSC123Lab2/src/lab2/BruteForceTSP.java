import java.util.ArrayList;
import java.util.List;

public class BruteForceTSP {
    private static int N; 
    private static int[][] distanceMatrix; 
    private static boolean[] visited; 
    private static int minCost = Integer.MAX_VALUE; 
    private static List<int[]> bestPaths = new ArrayList<>(); 

    public static void main(String[] args) {
        distanceMatrix = new int[][]{
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        N = distanceMatrix.length;
        visited = new boolean[N];

        int[] path = new int[N + 1]; // store current node
        visited[0] = true; // start sa city 0
        path[0] = 0; // pers city

        System.out.println("All Possible Paths with Costs:");
        tspBruteForce(path, 1, 0);

        // Print all best paths
        System.out.println("\nBest Paths:");
        System.out.println("Minimum Cost: " + minCost);
        for (int[] bestPath : bestPaths) {
            System.out.print("Path: ");
            for (int i = 0; i <= N; i++) {
                System.out.print(bestPath[i] + " ");
            }
            System.out.println();
        }
    }

    private static void tspBruteForce(int[] path, int level, int cost) {
        if (level == N) { // lahat ng city n nvisit
            cost += distanceMatrix[path[level - 1]][path[0]]; // balik simula
            printPath(path, cost); 
            
            if (cost < minCost) { // if may mahanap na lesser
                minCost = cost;
                bestPaths.clear(); 
                bestPaths.add(path.clone()); 
            } else if (cost == minCost) { // if same minimum so marami
                bestPaths.add(path.clone());
            }
            return;
        }

        for (int i = 1; i < N; i++) { // try ibang cities except sa first
            if (!visited[i]) {
                visited[i] = true;
                path[level] = i;
                tspBruteForce(path, level + 1, cost + distanceMatrix[path[level - 1]][i]);
                visited[i] = false; // magbacktrack
            }
        }
    }

    private static void printPath(int[] path, int cost) {
        for (int i = 0; i < N; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.println(path[0] + " | Cost: " + cost);
    }
}
