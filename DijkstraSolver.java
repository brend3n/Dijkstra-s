import java.util.*;
import java.io.*;

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
			
		// captures the size of the N x N matrix

		// int N = scan.nextInt();

	
		matrix = new int [N][N];
	
		// Reading in the text file and creating the matrix
	
		for (int i = 0; i < N; i++){
	
			int rowNum = scan.nextInt();
//			System.out.println("Row: " + rowNum);
		
			// Ignoring the tab
			scan.skip("\t");
//			matrix[0][i] = scan.nextInt();
//			scan.next().charAt(0);
					
			 String str = scan.nextLine();
//			 System.out.println("String: " + str);
			
			// Parsing the string
			 str = str.replace(",", " ");	
//			 System.out.println("String: " + str);

			System.out.println();

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










	public void printWeightMatrix (int size)
	{
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				System.out.print(matrix[i][j]); //+ ((matrix[i][j+1] == N) ? System.out.println() : System.out.print(" ") ));
			}
			System.out.println();
		}


	}

	

	public void printMatrixBoolean(int size){
	
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.println();
		}



	}

	public void printMatrixBooleanShortHand(int size){
	
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				// System.out.print(((matrix[i][j] == true) ? "t" : "f") + " ");
			}
			System.out.println();
		}



	}



	public void printMatrixInt(int size){
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				// System.out.print( ((matrix[i][j] == true) ? 1 : 0) + " ");			      	
			}
			System.out.println();
		}
			
	}
	





	public void findConstrainedShortestPaths(String filename, int s, int e)
	{
		
	


		return;
	}




	
	public static void main(String [] args) throws Exception
	{

		DijkstraSolver matrix = new DijkstraSolver("Toy.txt");

		//System.out.println("Boolean version\n");
		//adj.printMatrixBoolean(4);
		
		//System.out.println();
		//System.out.println("Boolean version condensed\n");
		//adj.printMatrixBooleanShortHand(4);

		//System.out.println();
		//System.out.println("Integer version\n");
		// adj.printMatrixInt(4);

		return;

	}
}

