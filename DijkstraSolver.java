import java.util.*;
import java.io.*;

class Node implements Comparable<Node>{

	int val;
	int dist;

	Node(int val, int dist){

		this.val = val;
		this.dist = dist;
	}
	public int compareTo(Node n){
		return this.dist - n.dist;
	}
	public String toString(){
		// return "[Value, Distance] =  ["+ val+", " +dist+ "]";
		 return "["+ val+", " +dist+ "]";

	}

}


public class DijkstraSolver 
{

	// Edit this number to adjust for the size of the graph
	int N = 7;
	// int N = 100;

	// Creating an adjacency Matrix 
	int [][] matrix;

	// Constuctor
	public DijkstraSolver (String fileName) throws Exception
	{

		// Creating a scanner object that will operate
		// on a file object with the passed in parameter
		// as the input file
		Scanner scan = new Scanner(new File(fileName));

		matrix = new int [N][N];
	
		// Reading in the text file and creating the matrix
	
		for (int i = 0; i < N; i++){
	
			int rowNum = scan.nextInt();
		
			// Ignoring the tab
			scan.skip("\t");
					
			String str = scan.nextLine();
		
			// Setting up the string for parsing
			str = str.replace(",", " ");	
	
			// Begin to parse string
			Scanner strScan = new Scanner(str);
			

			for (int j = 0; j < N; j++){
				if(strScan.hasNextInt())
					matrix[i][j] = strScan.nextInt();
				else
					strScan.skip(" ");
			}

			System.out.println(Arrays.toString(matrix[i]));

		}		
		
	}

	public static void writeToFile(String filename, String input){

		try{
			FileWriter writer = new FileWriter(filename);
			writer.write(input);
			writer.close();
			System.out.println("Input: " + input +" written to File: " + filename);
			System.out.println();

		}catch(IOException f){
			System.out.println("Error in: findContrainedShortestPaths(" + filename +")\n Exception: " + f);
		}		
		

	}
	public static void appendToFile(String filename, String input){
		try{	
			FileWriter writer = new FileWriter(filename, true);
			writer.write(input);
			writer.close();
			System.out.println("Input: " + input +" written to File: " + filename);
			System.out.println();
		}catch(IOException f){
			System.out.println("Error in: findContrainedShortestPaths(" + filename +")\n Exception: " + f);				
		}
	}

	public void findShortestPath(String filename, int s, int e)
	{
		// minheap to store all Node objects in increasing order
		PriorityQueue<Node> minNode = new PriorityQueue<Node>();

		// Used to see if there are any nodes left to visit
		int numNodesVisited = 0;

		// Stores the current node in a pass of Dijkstra's Algorithm
		int currentNode;
		
	       // Stores the distance for each vertex	
		int [] dist = new int [N];

		// Stores all of the nodes that have already been visited
		boolean [] visited = new boolean[N];


		// Initializing distances to each node to infinity
		for(int i = 0; i < N; i++){
			dist[i] = Integer.MAX_VALUE;
		}


		// Distance from end node to the end node is 0
		dist[e] = 0;

		// Add the end node to the list of visited nodes in the graph
		visited[e] = true;
		numNodesVisited++;


		// Adding all nodes into the minheap with a distance of infinity
		for(int i = 0; i < N; i++){
		
			if(i == e ){
				minNode.add(new Node(e, 0));
				continue;
			}
				
			minNode.add(new Node(i, Integer.MAX_VALUE));
		}
		
		
		while(!minNode.isEmpty() && numNodesVisited < N){
		
			// 1. Find the node with the lowest distance
			// 2. Make that node the current
			currentNode = minNode.remove().val;
			System.out.println("Node with the lowest distance: " + currentNode);

			// 3. Add it to visited
			
			visited[currentNode] = true;
			// 4. Find all adjacent nodes to current node
	
			

			// 5. For all adjacent nodes, update distances 
			System.out.println("in the while loop");
			break;

		}

		
//		System.out.println("minNode: " + minNode);
//		System.out.println("Dist[]: " + Arrays.toString(dist));
//		System.out.println("visited[]: " + Arrays.toString(visited));
//		System.out.println("Number of Nodes visited: " + numNodesVisited);
		print(minNode, dist, visited, numNodesVisited);
	
		return;
	}
	
	public static void print(PriorityQueue<Node> minNode, int [] dist, boolean [] visited, int numNodesVisited ){
	
		System.out.println("minNode: " + minNode);
		System.out.println();
		System.out.println("Dist[]: " + Arrays.toString(dist));
		System.out.println();
		System.out.println("visited[]: " + Arrays.toString(visited));
		System.out.println();
		System.out.println("Number of Nodes visited: " + numNodesVisited);
		System.out.println();

	}


	public static void main(String [] args) throws Exception
	{

		DijkstraSolver matrix = new DijkstraSolver("Toy.txt");
		matrix.findShortestPath("Toy.txt", 3, 3);
		writeToFile("out.txt", "Hi, Brenden\n");
		appendToFile("out.txt", "You are the coolest kid ever\n");
	
		return;
	}
}
