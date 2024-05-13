// Discrete Structure Group Project
import java.text.DecimalFormat;

class Station {
	static String[] stationName={"Kuala Lumpur","Putrajaya","Seremban","Ayer Keroh","Muar","Batu Pahat","Nusajaya","Jurong East"};
	private static final int NO_PARENT = -1;

	//find shortest path from KL - JE
	private static void shortestPath(int[][] adjacencyMatrix, int startVertex)  
	{
		int nVertices = adjacencyMatrix[0].length;
		int[] shortestDistances = new int[nVertices];
		boolean[] added = new boolean[nVertices];
		
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
		{
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}
		shortestDistances[startVertex] = 0;

		int[] parents = new int[nVertices];
		parents[startVertex] = NO_PARENT;

		for (int i = 1; i < nVertices; i++)
		{
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
			{
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance)
				{
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			added[nearestVertex] = true;

			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
			{
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
				
				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) <shortestDistances[vertexIndex]))
				{
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}
		printSolution(startVertex, shortestDistances, parents);
	}

	//display shortest path from KL - JE
	private static void printSolution(int startVertex, int[] distances, int[] parents)
	{
		int vertexIndex = 7;
		double minutes=distances[vertexIndex] * 0.257142;
		double fee=distances[vertexIndex] * 0.47;
		DecimalFormat df = new DecimalFormat("###.##");
		System.out.print("\n\nThe shortest path from Kuala Lumpur to Singapore(Jurong East)");
		System.out.print("\n\nStation(from->to):\t" + stationName[startVertex] + " -> " + stationName[vertexIndex]);
		System.out.print("\nShortest route:\t\t");
		printPath(vertexIndex, parents);
		System.out.print("\nPath no:\t\t10");
		System.out.print("\nDistance:\t\t" + distances[vertexIndex]+" km");
		System.out.print("\nDuration:\t\t" + df.format(minutes)+" minutes");
		System.out.print("\nFee:\t\t\tRM" + df.format(fee));
	}

	private static void printPath(int currentVertex, int[] parents)
	{
		if (currentVertex == NO_PARENT)
		{
			return;
		}
		printPath(parents[currentVertex], parents);
		System.out.print(stationName[currentVertex]);
			if (currentVertex != 7) {
				System.out.print(" - ");
			}
	}

	static int calculateDistance(int[][] adjacencyMatrix, int[] route) {
		// Initialize distance to 0
		int distance = 0;
		
		// Iterate over the elements in the route array
		for (int i = 0; i < route.length - 1; i++) {
		  // Add the distance between the current station and the next station to the distance
		  distance += adjacencyMatrix[route[i]][route[i+1]];
		}
		
		// Return the total distance
		return distance;
	  }

	static void displayTable(int[][] adjacencyMatrix) {
		String[] stationName = {"Kuala Lumpur","Putrajaya","Seremban","Ayer Keroh","Muar","Batu Pahat","Nusajaya","Jurong East"};
		
		int[][] routes = {
			{0, 1, 2, 3, 4, 5, 6, 7},  // route: Kuala Lumpur - Putrajaya - Seremban - Ayer Keroh - Muar - Batu Pahat - Nusajaya - Jurong East
			{0, 1, 2, 3, 4, 6, 7},  // route: Kuala Lumpur - Putrajaya - Seremban - Ayer Keroh - Muar - Nusajaya - Jurong East
			{0, 1, 2, 3, 5, 6, 7},  // route: Kuala Lumpur - Putrajaya - Seremban - Ayer Keroh - Batu Pahat  - Nusajaya - Jurong East
			{0, 1, 2, 4, 5, 6, 7},  // route: Kuala Lumpur - Putrajaya - Seremban - Muar - Batu Pahat  - Nusajaya - Jurong East
			{0, 1, 2, 4, 6, 7},  // route: Kuala Lumpur - Putrajaya - Seremban - Muar - Nusajaya - Jurong East
			{0, 2, 3, 4, 5, 6, 7},  // route: Kuala Lumpur - Seremban - Ayer Keroh - Muar - Batu Pahat - Nusajaya - Jurong East
			{0, 2, 3, 4, 6, 7},  // route: Kuala Lumpur - Seremban - Ayer Keroh - Muar - Nusajaya - Jurong East
			{0, 2, 3, 5, 6, 7},  // route: Kuala Lumpur - Seremban - Ayer Keroh - Batu Pahat - Nusajaya - Jurong East
			{0, 2, 4, 5, 6, 7},  // route: Kuala Lumpur - Seremban - Muar - Batu Pahat - Nusajaya - Jurong East
			{0, 2, 4, 6, 7}  // route: Kuala Lumpur - Seremban - Muar - Nusajaya - Jurong East
		};
	  
		//display each path and its route, distance, duration and fee
		System.out.println("\nPath No.\t\tRoute\t\t\t\t\t\t\t\t\t\tTotal Distance (km)\tDuration (minutes)\t   Fee (RM)");
		System.out.println("-------\t\t\t-----\t\t\t\t\t\t\t\t\t\t-----------------\t-----------------\t   -------");

		int counter = 1;
		for (int[] route : routes) {
			int distance = calculateDistance(adjacencyMatrix, route);
			double duration = distance * 0.257142;
			double fee = distance * 0.47;

			String routeString = "";
			for (int i : route) {
				routeString += stationName[i] + " - ";
			}
			routeString = routeString.substring(0, routeString.length() - 3); // remove the last " - "

			System.out.printf("%-11d%-99s%-22d%-24.2f%-15.2f\n", counter, routeString, distance, duration, fee);
			counter++;
		}
	}

	public static void main(String[] args)
	{
		int[][] adjacencyMatrix = { { 0,   20, 60,  0,  0,   0,   0,   0 }, //stations location in matrix
									{ 20,  0,  50,  0,  0,   0,   0,   0 },
									{ 60,  50, 0,   65, 100, 0,   0,   0 },
									{ 0,   0,  65,  0,  45,  85,  0,   0 },
									{ 0,   0,  100, 45, 0,   50,  145, 0 },
									{ 0,   0,  0,   85, 50,  0,   105, 0 },
									{ 0,   0,  0,   0,  145, 105, 0,   15 },
									{ 0,   0,  0,   0,  0,   0,   15,  0 }, };
		System.out.print("\nSystem to calculate the shortest path from Kuala Lumpur to Singapore(Jurong East)\n\n");

		displayTable(adjacencyMatrix);
		shortestPath(adjacencyMatrix, 0);
	}
}