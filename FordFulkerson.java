import java.util.*;
import java.io.*;
import java.lang.*;


public class FordFulkerson{

    static final int numOfVertices = 7;

    public static void main(String[] args) {

        int[][] capacityMatrix = new int[][]{{0, 8, 0, 0, 7, 0, 0},
                {0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 10, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 9},
                {0, 0, 3, 0, 0, 5, 0},
                {0, 0, 0, 0, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0}};  //where element ij is the flow capacity from i to j

        FordFulkerson f = new FordFulkerson();
        System.out.println("Max flow is " + f.fordFulkerson(capacityMatrix, 0, 6));

    }

    public static int fordFulkerson(int[][] graph, int s, int t){

        int maxFlow = 0;

        int[][] remainingCapacity = new int[numOfVertices][numOfVertices]; //keep track of remaining capacity of edges as flow increases

        for(int i=0; i<numOfVertices; i++){
            for(int j=0; j<numOfVertices; j++){
                remainingCapacity[i][j] = graph[i][j]; //initialize remainingCapacity
            }
        }

        int[] predecessor = new int[numOfVertices]; //track pfip

        while(bfs(remainingCapacity, s, t, predecessor)) { //if pfip exists
            int pathFlow = Integer.MAX_VALUE;

            for (int i = t; i != s; i = predecessor[i]) { //find min increasing pfip
                int temp = predecessor[i];
                pathFlow = Math.min(pathFlow, remainingCapacity[temp][i]);
            }

            for (int i = t; i != s; i = predecessor[i]) { //update matrix
                int temp = predecessor[i];
                remainingCapacity[temp][i] -= pathFlow;
                remainingCapacity[i][temp] += pathFlow;
            }

            maxFlow += pathFlow; //increment flow

        }

        return maxFlow;

        }

        public static boolean bfs(int graph[][], int s, int t, int[] predecessor){ //breadth first search to see if a pfip exists in remaining capacity graph
            boolean[] visited = new boolean[numOfVertices];

            for(int i=0; i<numOfVertices; i++){
                visited[i] = false;
            }

            LinkedList<Integer> path = new LinkedList<Integer>();
            path.add(s); //add vertices to a queue
            visited[s] = true;
            predecessor[s] = -1;

            while(path.size() != 0){

                int a = path.remove();

                for(int i=0; i<numOfVertices; i++){
                    if((graph[a][i] > 0) && visited[i] == false){ //if there is remaining capacity from a to i, add i to the path
                        path.add(i);
                        visited[i] = true;
                        predecessor[i] = a;

                    }
                }
            }

            return (visited[t] == true);

    }

}
