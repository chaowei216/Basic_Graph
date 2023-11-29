/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Graph {

    private int V;
    private int E;
    private List<String> verList;
    private int[][] adjMatrix;
    private List<String> edgeList;
    private List<List<String>> adjList;
    private int[][] incMatrix;
    private List<Integer> edgeWeightList;

    public Graph(int V, int E, List<String> verL) {
        this.V = V;
        this.E = E;
        verList = verL;
        adjList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            List<String> list = new ArrayList<>();
            list.add(verList.get(i));
            adjList.add(list);
        }
    }

    public Graph(String file_path) {
        verList = new ArrayList<>();
        adjList = new ArrayList<>();
        edgeList = new ArrayList<>();
        edgeWeightList = new ArrayList<>();
        loadDataFromFile(file_path);
    }

    public List<String> getVerList() {
        return this.verList;
    }

    public List<String> getEdgeList() {
        return this.edgeList;
    }

    public int getV() {
        return this.V;
    }

    public int getE() {
        return this.E;
    }
    
    public List<Integer> getEdgeWeightList() {
        return this.edgeWeightList;
    }

    public void showIncidentMatrix() {
        System.out.print("  ");
        for (String key : this.edgeList) {
            System.out.print(key + " ");
        }
        System.out.println("");
        for (int i = 0; i < V; i++) {
            System.out.print(this.verList.get(i) + " ");
            for (int j = 0; j < E; j++) {
                System.out.print(incMatrix[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void showAdjacencyList() {
        int count = 0;
        for (List<String> list : this.adjList) {
            System.out.print("Adjacency List " + (count+1) + "th vertex: ");
            for (int i = 0; i < list.size(); i++) {
                if(i == list.size() - 1) {
                    System.out.println(list.get(i));
                    break;
                }
                System.out.print(list.get(i) + " -> ");
            }
            System.out.println(" ");
            count++;
        }
    }

    public void showAdjacencyMatrix() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(this.adjMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    //convert from adj matrix to inc matrix
    public void Convert1() {
        this.edgeList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (adjMatrix[i][j] != 0) {
                    String edge = verList.get(i).concat(verList.get(j));
                    if (!this.edgeList.contains(verList.get(j).concat(verList.
                            get(i)))) {
                        this.edgeWeightList.add(adjMatrix[i][j]);
                        this.edgeList.add(edge);
                    }
                }
            }
        }
        //num of edge
        this.E = this.edgeList.size();

//        incident matrix
        this.incMatrix = new int[V][E];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < E; j++) {
                if (this.edgeList.get(j).contains(this.verList.get(i))) {
                    incMatrix[i][j] = 1;
                } else {
                    incMatrix[i][j] = 0;
                }
            }
        }
        showIncidentMatrix();
    }

    //convert from adjacency matrix to adjacency list
    public void Convert2() {
        this.adjList = new ArrayList<>();
        for(int i = 0; i < this.V; i++) {
            List<String> list = new ArrayList<>();
            list.add(this.verList.get(i));
            this.adjList.add(list);
        }
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (adjMatrix[i][j] > 0) {
                    adjList.get(i).add(this.verList.get(j));
                }
            }
        }
        showAdjacencyList();
    }

    public void BFS(String value) {
        if (!this.verList.contains(value)) {
            System.out.println("Vertex does not exist");
            return;
        }
        System.out.print("Breadth First Search: ");
        boolean[] visited = new boolean[V];
        Queue<String> queue = new LinkedList<>();
        visited[this.verList.indexOf(value)] = true;
        queue.add(value);
        while (!queue.isEmpty()) {
            String u = queue.poll();
            System.out.print(u + " ");
            for (String v : adjList.get(this.verList.indexOf(u))) {
                if (!visited[this.verList.indexOf(v)]) {
                    visited[this.verList.indexOf(v)] = true;
                    queue.add(v);
                }
            }
        }
    }

    public void DFS(String value) {
        System.out.print("Depth First Search: ");
        boolean[] visited = new boolean[V];
        DFSUtil(value, visited);
    }

    private void DFSUtil(String value, boolean[] visited) {
        if (!this.verList.contains(value)) {
            System.out.println("Vertex does not exist");
            return;
        }
        visited[this.verList.indexOf(value)] = true;
        System.out.print(value + " ");
        for (String v : adjList.get(verList.indexOf(value))) {
            if (!visited[verList.indexOf(v)]) {
                DFSUtil(v, visited);
            }
        }
    }

    public List<String> Findpath(String src, String dest) {
        if(!verList.contains(src) || !verList.contains(dest)) {
            System.out.println("Non-existed Vertex!!!");
            return null;
        }
        // initialize distance and parent arrays
        int[] dist = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // mark source vertex distance as 0
        dist[verList.indexOf(src)] = 0;

        // create a priority queue to hold vertices by distance from source
        PriorityQueue<String> pq = new PriorityQueue<>(V,
                new Comparator<String>() {
            @Override
            public int compare(String u, String v) {
                return dist[verList.indexOf(u)] - dist[verList.indexOf(v)];
            }
        });

        pq.offer(src); // add source vertex to priority queue

        // iterate until priority queue is empty
        while (!pq.isEmpty()) {
            String u = pq.poll();

            // iterate through neighbors of u
            for (int v = 0; v < V; v++) {
                if (adjMatrix[verList.indexOf(u)][v] != 0) { // v is a neighbor of u
                    int alt = dist[verList.indexOf(u)] + adjMatrix[verList.indexOf(u)][v]; // calculate new distance
                    if (alt < dist[v]) { // if new distance is shorter
                        dist[v] = alt; // update distance
                        parent[v] = verList.indexOf(u); // update parent
                        pq.offer(verList.get(v)); // add v to priority queue
                    }
                }
            }
        }

        // construct path from dest to src using parent array
        List<String> path = new ArrayList<>();
        int u = verList.indexOf(dest);
        while (u != -1) {
            path.add(verList.get(u));
            u = parent[u];
        }
        Collections.reverse(path); // reverse path from src to dest

        return (path.get(0).equals(src)) ? path : null; // return path or null if dest is unreachable
    }
    
    //MST1
    public int minKey(int key[], Boolean mstSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }

        return min_index;
    }
    
    public Graph MST1(String src) {
        Graph mst = new Graph(V, E, verList);
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean mstSet[] = new Boolean[V];
        
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        
        key[verList.indexOf(src)] = 0;
        parent[verList.indexOf(src)] = -1;
        
        for(int count = 0; count < V; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            
            for(int v = 0; v < V; v++) {
                if(adjMatrix[u][v] != 0 && mstSet[v] == false && adjMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjMatrix[u][v];
                }
            }
        }
        
        printMST(parent, adjMatrix);
        return mst;
    }
    
    public void printMST(int parent[], int graph[][]) {
        System.out.println("Edge \tWeight");
        int sum = 0;
        for(int i = 0; i < V; i++) {
            if(parent[i] == -1) continue;
            sum += graph[i][parent[i]];
            System.out.println(verList.get(parent[i]) + " - " + verList.get(i) + "\t" + graph[i][parent[i]]);
        }
        System.out.println("=> Total weight: " + sum);
    }
    
    //////Krusalll
    public void MST2() {
        KruskalAlgorithm kru = new KruskalAlgorithm(this.V, this.E, this.verList);
        int tmp = 0;
        for(int i = 0; i < kru.vertices; i++) {
            for(int j = 0; j < kru.vertices; j++) {
                if(this.adjMatrix[i][j] != 0) {
                    int count = 0;
                    for(int z = 0; z < kru.edgeArray.length; z++) {
                        if(kru.edgeArray[z].source == j && kru.edgeArray[z].destination == i) {
                            count++;
                            break;
                        }
                    }
                    if(count == 0) {
                        kru.edgeArray[tmp].source = i;
                        kru.edgeArray[tmp].destination = j;
                        kru.edgeArray[tmp].weight = this.adjMatrix[i][j];
                        tmp++;
                    }
                }
            }
        }
        kru.applyKruskal();
    }
    
    class KruskalAlgorithm {
        //create class Edge to create an edge of the graph that implements Comparable interface   

        class Edge implements Comparable<Edge> {

            int source, destination, weight;

            public int compareTo(Edge edgeToCompare) {
                return this.weight - edgeToCompare.weight;
            }
        };

        // create class Subset for union  
        class Subset {

            int parent, value;
        };

        //initialize vertices, edges and edgeArray  
        int vertices, edges;
        Edge edgeArray[];
        List<String> verL;

        // using constructor to create a graph  
        KruskalAlgorithm(int vertices, int edges, List<String> vList) {
            this.vertices = vertices;
            this.edges = edges;
            edgeArray = new Edge[this.edges];
            for (int i = 0; i < edges; ++i) {
                edgeArray[i] = new Edge();  //create edge for all te edges given by the user  
            }
            this.verL = vList;
        }

        // create applyKruskal() method for applying Kruskal's Algorithm  
        void applyKruskal() {

            // initialize finalResult array to store the final MST  
            Edge finalResult[] = new Edge[vertices];
            int newEdge = 0;
            int index = 0;
            for (index = 0; index < vertices; ++index) {
                finalResult[index] = new Edge();
            }

            // using sort() method for sorting the edges  
            Arrays.sort(edgeArray);

            // create an array of the vertices of type Subset for the subsets of vertices  
            Subset subsetArray[] = new Subset[vertices];

            // aloocate memory to create vertices subsets  
            for (index = 0; index < vertices; ++index) {
                subsetArray[index] = new Subset();
            }

            // it is used to create subset with single element  
            for (int vertex = 0; vertex < vertices; ++vertex) {
                subsetArray[vertex].parent = vertex;
                subsetArray[vertex].value = 0;
            }
            index = 0;

            // use for loop to pick the smallers edge from the edges and increment the index for next iteration  
            while (newEdge < vertices - 1) {
                // create an instance of Edge for next edge  
                Edge nextEdge = new Edge();
                nextEdge = edgeArray[index++];

                int nextSource = findSetOfElement(subsetArray, nextEdge.source);
                int nextDestination = findSetOfElement(subsetArray,
                        nextEdge.destination);

                //if the edge doesn't create cycle after including it, we add it in the result and increment the index  
                if (nextSource != nextDestination) {
                    finalResult[newEdge++] = nextEdge;
                    performUnion(subsetArray, nextSource, nextDestination);
                }
            }
            int sum = 0;
            for (index = 0; index < newEdge; ++index) {
                System.out.println(
                        this.verL.get(finalResult[index].source) + " - " + this.verL.get(finalResult[index].destination) + ": " + finalResult[index].weight);
                sum+=finalResult[index].weight;
            }
            System.out.println("=> Total weight: " + sum);
        }

        // create findSetOfElement() method to get set of an element  
        int findSetOfElement(Subset subsetArray[], int i) {
            if (subsetArray[i].parent != i) {
                subsetArray[i].parent = findSetOfElement(subsetArray,
                        subsetArray[i].parent);
            }
            return subsetArray[i].parent;
        }

        // create performUnion() method to perform union of two sets  
        void performUnion(Subset subsetArray[], int sourceRoot, int destinationRoot) {

            int nextSourceRoot = findSetOfElement(subsetArray, sourceRoot);
            int nextDestinationRoot = findSetOfElement(subsetArray,
                    destinationRoot);

            if (subsetArray[nextSourceRoot].value < subsetArray[nextDestinationRoot].value) {
                subsetArray[nextSourceRoot].parent = nextDestinationRoot;
            } else if (subsetArray[nextSourceRoot].value > subsetArray[nextDestinationRoot].value) {
                subsetArray[nextDestinationRoot].parent = nextSourceRoot;
            } else {
                subsetArray[nextDestinationRoot].parent = nextSourceRoot;
                subsetArray[nextSourceRoot].value++;
            }
        }
    }
  
/////////////////////////////////////////////
    public boolean isEulerian() {
        // check if all vertices have even degree
        for (int i = 0; i < V; i++) {
            int degree = 0;
            for (int j = 0; j < V; j++) {
                if(this.adjMatrix[i][j] != 0)
                degree++;
            }
            if (degree % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public List<String> Euler(String start) {
        // check if graph is Eulerian
        if (!isEulerian()) {
            return null;
        }
        // create a copy of the adjacency matrix
        int[][] adj = Arrays.copyOf(this.adjMatrix, this.V);

        // create a stack to store the vertices in the cycle
        Stack<String> stack = new Stack<>();

        // push the starting vertex onto the stack
        stack.push(start);

        // create a list to store the vertices in the cycle
        List<String> cycle = new ArrayList<>();

        // while the stack is not empty
        while (!stack.isEmpty()) {
            // get the top vertex on the stack
            String u = stack.peek();
            // find an adjacent vertex
            int v = -1;
            for (int i = 0; i < V; i++) {
                if (adj[verList.indexOf(u)][i] != 0) {
                    v = i;
                    break;
                }
            }
            // if no adjacent vertex found, pop the stack and add vertex to cycle
            if (v == -1) {
                stack.pop();
                cycle.add(u);
            } else {
                // remove edge (u, v) from the adjacency matrix
                adj[verList.indexOf(u)][v] = 0;
                adj[v][verList.indexOf(u)] = 0;

                // push vertex v onto the stack
                stack.push(verList.get(v));
            }
        }

        // reverse the list to get the cycle in the correct order
        Collections.reverse(cycle);
        return cycle;
    }

    private void loadDataFromFile(String file_path) {
        String verLine = "";
        List<String> verDataList = new ArrayList();
        List<String> matrixList = new ArrayList();
        try (Scanner sc = new Scanner(new File(file_path))) {
            String entityString;
            entityString = sc.nextLine();
            if (!entityString.trim().isEmpty()) {
                verLine = entityString;
            }
            while (sc.hasNextLine()) {
                entityString = sc.nextLine();
                if (!entityString.trim().isEmpty()) {
                    matrixList.add(entityString);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        String[] verLineList = verLine.split("\\s");
        for (String ver : verLineList) {
            verDataList.add(ver);
        }
        //list of vertex
        for (String str : verDataList) {
            this.verList.add(str.trim());
        }

        //num of vertex
        this.V = this.verList.size();

        //adjacency matrix
        this.adjMatrix = new int[V][V];
        int tmp = 0;
        for (int i = 0; i < matrixList.size(); i++) {
            String[] strList = matrixList.get(i).split("\\s");
            for (int z = 0; z < strList.length; z++) {
                Integer value = Integer.parseInt(strList[z].trim());
                this.adjMatrix[tmp][z] = value;
            }
            tmp++;
        }

        //adjlist
        for (int i = 0; i < matrixList.size(); i++) {
            String[] list = matrixList.get(i).split("\\s");
            int tmp1 = 0;
            List<String> list1 = new ArrayList<>();
            list1.add(verList.get(i));
            for (String value : list) {
                int newValue = Integer.parseInt(value.trim());
                if (newValue != 0) {
                    list1.add(this.verList.get(tmp1));
                    tmp1++;
                } else {
                    tmp1++;
                }
            }
            this.adjList.add(list1);
        }

        //list of edge
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (adjMatrix[i][j] != 0) {
                    String edge = verList.get(i).concat(verList.get(j));
                    if (!this.edgeList.contains(verList.get(j).concat(verList.
                            get(i)))) {
                        this.edgeWeightList.add(adjMatrix[i][j]);
                        this.edgeList.add(edge);
                    }
                }
            }
        }
        //num of edge
        this.E = this.edgeList.size();

//        incident matrix
        this.incMatrix = new int[V][E];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < E; j++) {
                if (this.edgeList.get(j).contains(this.verList.get(i))) {
                    incMatrix[i][j] = 1;
                } else {
                    incMatrix[i][j] = 0;
                }
            }
        }
    }
}
