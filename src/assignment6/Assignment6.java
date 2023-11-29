/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment6;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Assignment6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        int choice;
        Graph graph = new Graph("Data.txt");
        do {            
            System.out.println("\t\tAssignment 6");
            System.out.println("1. Show num of vertex, list of vertex");
            System.out.println("2. Show num of edge, list of edge");
            System.out.println("3. Show adjacency list");
            System.out.println("4. Show adjacency matrix");
            System.out.println("5. Show incident matrix");
            System.out.println("6. Convert 1");
            System.out.println("7. Convert 2");
            System.out.println("8. BFS");
            System.out.println("9. DFS");
            System.out.println("10. Find path");
            System.out.println("11. Prim algorithm");
            System.out.println("12. Kruskal algorithm");
            System.out.println("13. Euler");
            System.out.println("14. Quit");
            System.out.println("");
            System.out.print("Please enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());
            System.out.println("");
            
            switch(choice) {
                case 1: {
                    System.out.println("Num of vertex: " + graph.getV());
                    System.out.print("List vertex: ");
                    for(String value : graph.getVerList()) {
                        System.out.print(value + " ");
                    }
                    System.out.println("\n");
                    break;
                }
                
                case 2: {
                    System.out.println("Num of edge: " + graph.getE());
                    System.out.print("List of edge: ");
                    for(String value : graph.getEdgeList()) {
                        System.out.print(value + " ");
                    }
                    System.out.println("\n");
                    break;
                }
                
                case 3: {
                    System.out.println("Show Adjacency List");
                    graph.showAdjacencyList();
                    break;
                }
                
                case 4: {
                    System.out.println("Show adjacency matrix");
                    graph.showAdjacencyMatrix();
                    break;
                }
                
                case 5: {
                    System.out.println("Show incident matrix");
                    graph.showIncidentMatrix();
                    break;
                }
                
                case 6: {
                    System.out.println("Convert from adjacency matrix to incident matrix");
                    graph.Convert1();
                    break;
                }
                
                case 7: {
                    System.out.println("Convert from adjacency matrix to adjacency list");
                    graph.Convert2();
                    break;
                }
                
                case 8: {
                    System.out.print("Enter source vertex: ");
                    String src = sc.nextLine().toUpperCase();
                    graph.BFS(src);
                    System.out.println("\n");
                    break;
                }
                
                case 9: {
                    System.out.print("Enter source vertex: ");
                    String src = sc.nextLine().toUpperCase();
                    graph.DFS(src);
                    System.out.println("\n");
                    break;
                }
                
                case 10: {
                    System.out.print("Enter 1st vertex: ");
                    String ver1 = sc.nextLine().toUpperCase();
                    System.out.print("Enter 2nd vertex: ");
                    String ver2 = sc.nextLine().toUpperCase();
                    for(String value : graph.Findpath(ver1, ver2)) {
                        System.out.print(value + " ");
                    }
                    System.out.println("\n");
                    break;
                }
                
                case 11: {
                    System.out.print("Enter src vertex: ");
                    String ver = sc.nextLine().toUpperCase();
                    System.out.println("Prim-Janik algorithm:");
                    Graph mst = graph.MST1(ver);
                    break;
                }
                
                case 12: {
                    System.out.println("Kruskal algorithm:");
                    graph.MST2();
                    break;
                }
                
                case 13: {
                    System.out.print("Enter src vertex: ");
                    String ver = sc.nextLine().toUpperCase();
                    System.out.println("Euler:");
                    for(String value : graph.Euler(ver)) {
                        System.out.print(value + " ");
                    }
                    graph = new Graph("Data.txt");
                    System.out.println("\n");
                    break;
                }
                
                case 14: break;
            }
        } while (choice != 14);
    }
    
}
