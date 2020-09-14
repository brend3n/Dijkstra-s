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


	String [] cities = {"SF", "SE", "DA", "DE", "CH", "AT", "DC"};

	// Constuctor
	public DijkstraSolver (String fileName) throws Exception
	{

		// Creating a scanner object that will operate
		// on a file object with the passed in parameter
		// as the input file
		Scanner scan = new Scanner(new File(fileName));

		matrix = new int [N][N];


		// Formatting for debugging
		System.out.print("   ");
		for(int i = 0; i < N; i++){
			System.out.print(i + "  ");
		}
		System.out.println();


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

			System.out.println(i + " " + Arrays.toString(matrix[i]));

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

	public int findShortestPath(String filename, int s, int e)
	{
	//	Result result = new Result(s, e);

		// minheap to store all Node objects in increasing order
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

		previous.put(e, null);

		// Adding all nodes into the minheap with a distance of infinity
		for(int i = 0; i < N; i++)
			minNode.add(new Node(i, dist[i]));

		System.out.println("MINNODE: " + minNode);

		while(!minNode.isEmpty() && numNodesVisited < N){

			// 1. Find the node with the lowest distance
			// 2. Make that node the current
			currentNode = minNode.remove().val;

			// 3. Add it to visited
			visited[currentNode] = true;
			numNodesVisited++;

			// 4. Find all adjacent nodes to current node
			// 5. For all adjacent nodes, update distances

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

		print(minNode, dist, visited, numNodesVisited, cities, previous);

		String stringy = reconstructPath(previous, s, e);

		int totalWeight = dist[s];

		return totalWeight;
	}

	public static void print(PriorityQueue<Node> minNode, int [] dist, boolean [] visited, int numNodesVisited, String [] cities, HashMap<Integer, Integer> previous){

		System.out.println("minNode: " + minNode);
		System.out.println();
		// System.out.println("Dist[]: " + Arrays.toString(dist));
		for(int i = 0; i < dist.length; i++)
		{
			System.out.println(cities[i] + ": " + dist[i]);
		}

		System.out.println();
		System.out.println("visited[]: " + Arrays.toString(visited));
		System.out.println();
		System.out.println("Number of Nodes visited: " + numNodesVisited);
		System.out.println();
		System.out.println("HashMap: " + previous);
		System.out.println();
			

		System.out.println("{Key, Value}");
		for(int i = 0; i < dist.length; i++){

			System.out.println("{" +cities[i]+"->"+((previous.get(i) == null)? -1 :cities[previous.get(i)])+ "}");
		}


	}

	public static String reconstructPath(HashMap<Integer, Integer> map, int source, int end){
		ArrayList<Integer> pathArr = new ArrayList<Integer>();
		String pathStr = null;
		int current;
		int i = 0;

	//	Arrays.fill(pathArr, -1);

		// 1. current <- source key
		// 2. add current to array
		// 3. current <- value(current) 
		// 4. Go to step 2
		
		current = source;

		//while(map.get(current) != null){
		//	pathArr[i++] = current; 
		//	current = map.get(current);
		//}
		

		do{
			pathArr.add(current);		
			current = map.get(current);
		}while(map.get(current) != null);

		pathStr = (Arrays.toString(pathArr.toArray()));
 
		pathStr = pathStr.replace("[", "");
		pathStr = pathStr.replace("]", "");
		pathStr = pathStr.replace("," , "");		
		System.out.println("pathStr: '" + pathStr + "'");

		pathStr = pathStr + " " + String.valueOf(end);
		System.out.println("pathStr: '" + pathStr + "'");

		
		return pathStr;
	}
	public static void main(String [] args) throws Exception
	{


		int [] start = new int[5];
		int [] end = new int[5];
		int [] PID = {4,1,9,4,7,8,5};
		for(int i = 0; (i+2) < PID.length; i++){
			start[i] = 10*PID[i] + PID[i+1];
			end[i] = 10*PID[i+1] + PID[i+2];
		}

		// Find the shortest path for each i : {start[i], end[i]}

		DijkstraSolver matrix = new DijkstraSolver("Toy.txt");
		matrix.findShortestPath("Toy.txt", 0, 6);


		// Write to a file in a loop for each i : {start[i], end[i]}

		//	writeToFile("out.txt", sourceToEnd);
		//	appendToFile("out.txt", shortestPath);
		//	appendToFile("out.txt", totalWeight);

		return;
	}
}
