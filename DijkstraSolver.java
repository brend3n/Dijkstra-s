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
		 return "["+ val+", " +dist+ "]";
	}

}


public class DijkstraSolver
{
	// Stores the number of nodes in a the graph
	int N;

	// Creating an adjacency Matrix
	int [][] matrix;

	// Constuctor
	public DijkstraSolver (String fileName, int numVertices) throws Exception
	{

		// Opens the passed in file and allows the filed to be read
		Scanner scan = new Scanner(new File(fileName));

		// Storing the number of vertices/nodes in the graph
		this.N = numVertices;

		// Declaring a 2-D  integer array to act as the adjacency matrix
		matrix = new int[numVertices][numVertices];


		// Reading in the text file and creating the matrix
		for (int i = 0; i < N; i++){

			// Reading in the first charater of each row. This represents the number of the row starting at 0.
			int rowNum = scan.nextInt();

			// Ignoring the tab
			scan.skip("\t");
				
			// Capture the entire row as a String to be parsed
			String str = scan.nextLine();

			// Setting up the string for parsing.
			// Replacing deleting all commas
			str = str.replace(",", " ");

			// Begin to parse string
			Scanner strScan = new Scanner(str);

			// Reading each edge in the text file and adding it to the adjacency matrix
			for (int j = 0; j < N; j++){
				if(strScan.hasNextInt())
					matrix[i][j] = strScan.nextInt();
				else
					strScan.skip(" ");
			}
		}
	}

	// Writes the input string (input) into the first line of the output file (filename)
	public static void writeToFile(String filename, String input){

		try{
			FileWriter writer = new FileWriter(filename);
			writer.write(input + "\n");
			writer.close();
			System.out.println("Input: " + input +" written to File: " + filename);
			System.out.println();

		}catch(IOException f){
			System.out.println("Error in: findContrainedShortestPaths(" + filename +")\n Exception: " + f);
		}


	}

	// Appends the input string (input) to the output file (filename)
	public static void appendToFile(String filename, String input){
		try{
			FileWriter writer = new FileWriter(filename, true);
			writer.write(input);
			writer.write("\n");
			writer.close();
			System.out.println("Input: " + input +" appended to File: " + filename);
			System.out.println();
		}catch(IOException f){
			System.out.println("Error in: findContrainedShortestPaths(" + filename +")\n Exception: " + f);
		}
	}

	// Prints the shortest path between the source vertex/node (s) and the end vertex/node (e)
	public void findShortestPath(int s, int e)
	{

		// Minheap to store all Node objects in increasing order
		PriorityQueue<Node> minNode = new PriorityQueue<Node>();

		// Stores the previous node for every node
		// Allows to reconstruct the shortest path
		HashMap<Integer, Integer> previous = new HashMap<>();

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

		// Used to recover the shortest path. Since the end node is the first node inserted
		// into the path recovery structure, it has no previous node. Thus, it is set to null.
		previous.put(e, null);

		// Adding all nodes into the minheap with a distance of infinity
		for(int i = 0; i < N; i++)
			minNode.add(new Node(i, dist[i]));

		// Loops as long as there are nodes to be visited
		while(!minNode.isEmpty() ||  numNodesVisited <= N){

			// 1. Find the node with the lowest distance
			// 2. Make that node the current
	
			// Get node with the lowest distance
			currentNode = minNode.remove().val;

			// 3. Add it to visited
			visited[currentNode] = true;
			numNodesVisited++;

			// 4. Find all adjacent nodes to current node
			// 5. For all adjacent nodes, update distances

			// For all adjacent nodes, update their distances if a shorter path is found
			for(int i = 0; i < N; i++){
				if(matrix[currentNode][i] > 0 && !visited[i]){
					if(dist[currentNode] + matrix[currentNode][i] < dist[i]){
						dist[i] = dist[currentNode] + matrix[currentNode][i];
						minNode.add(new Node(i, dist[i]));
						previous.put(i, currentNode);
					}
				}
			}
		}

		// Constructing the string that shows the source and target node that will be passed into the algorithm
		// This is the format of the string: Source, Target
		String sourceToEnd = String.valueOf(s) +", " +  String.valueOf(e);

		// Stores the reconstructed path that this implementation of Dijkstra's shortest path algorithm takes
		String shortestPath = reconstructPath(previous, s, e);

		int totalWeight = dist[s];


		// Writing output to file
		writeToFile("output.txt", sourceToEnd);
		appendToFile("output.txt", shortestPath);
		appendToFile("output.txt", String.valueOf(totalWeight));

	}

	// Recovers the path from this implementation of Dijkstra's shortest path algorithm
	public static String reconstructPath(HashMap<Integer, Integer> map, int source, int end){

		ArrayList<Integer> pathArr = new ArrayList<Integer>();
		String pathStr = null;
		int current;
		int i = 0;

		// The first element of the path should be the source node
		current = source;
		
		// Loops until it finds the end/targer node.
		// There is no previous node of the end/target vertex due to the implementation of the findShortestPath method.
		// This implementation works backwards from the end/target node to the source node.
		// The previous node of the target node is null.
		do{
			// Add node to the path
			pathArr.add(current);


			// Checks for if the node has reached the end/target node or if there is a path with only 1 node
			if(source == end)
				break;
			if(map.get(current) == null)
				break;

			// Get the previous node of this node
			current = map.get(current);

		}while(map.get(current) != null);

		// Append the end/target node to the path
		pathArr.add(end);

		// Convert the ArrayList into a string
		pathStr = (Arrays.toString(pathArr.toArray()));

		// Removing the brackets from the string pathStr
		pathStr = pathStr.replace("[", "");
		pathStr = pathStr.replace("]", "");


		return pathStr;
	}

	public static void main(String [] args) throws Exception
	{

		if (args.length < 4 ){
			System.out.println("To run Dijkstra's Algorithm");
			System.out.println("\t javac DijkstraSolver [filename] [source] [end] [number of vertices in graph] ");
		}else{


			String fileName = args[0];
			int source = Integer.parseInt(args[1]);
			int end = Integer.parseInt(args[2]);
			int numberOfVertices = Integer.parseInt(args[3]);

			DijkstraSolver matrix = new DijkstraSolver(fileName, numberOfVertices);
			matrix.findShortestPath(source, end);
		}

		return;
	}
}
