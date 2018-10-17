import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

// Create a route and evolve a solution
public class Main {
	private static ArrayList<String> locations;
	private static double[][] coords;
	private static int size;

	public static void main(String[] args) throws Exception {
		loadFile(new File("locations.txt"));

		// Convert input to correct format
		arrayListToDouble();

		// Declare Infinity
		final double INFINITY = Double.POSITIVE_INFINITY;

		// Add input into initial Route
		for (int i = 0; i < size; i++) {
			Part part = new Part((int) coords[i][0], coords[i][1], coords[i][2]);
			RouteManager.addPart(part);
		}

		// Initialise population(s)
		int popSize = size;
		Population[] popArray = new Population[popSize];

		for (int i = 0; i < popSize; i++) {
			popArray[i] = new Population(size, true);
			popArray[i] = GA.evolvePopulation(popArray[i]);
		}

		// For use in finding shortest route
		double sh, shortest = INFINITY;
		double[] dist = new double[popSize];
		int pos = 0;

		// Evolve population for 300k generations
		for (int i = 0; true; i++) {
			sh = INFINITY;

			for (int j = 0; j < popSize; j++) {
				popArray[j] = GA.evolvePopulation(popArray[j]);
				dist[j] = popArray[j].getFittest().getDistance();

				if (dist[j] < sh) {
					sh = dist[j];
					pos = j;
				}
			}

			// If new shortest route is found, print and update
			if(i % 10000 == 0) {
				while (sh < shortest) {
					shortest = sh;
					printShortest(i, shortest, popArray[pos]);
				}
			}
		}
	}

	// Read in input file
	private static void loadFile(File aFile) throws Exception {
		BufferedReader input = new BufferedReader(new FileReader(aFile));
		String line = null;
		locations = new ArrayList<String>();

		while ((line = input.readLine()) != null) {
			locations.add(line);
		}

		input.close();
		size = locations.size();
	}

	// Convert input to usable format
	public static void arrayListToDouble() {
		String[][] temp = new String[size][4];
		coords = new double[size][3];

		for (int i = 0; i < size; i++) {
			temp[i] = locations.get(i).split(",");
			coords[i][0] = Double.parseDouble(temp[i][0]);
			coords[i][1] = Double.parseDouble(temp[i][2]);
			coords[i][2] = Double.parseDouble(temp[i][3]);
		}
	}

	// Custom print method
	public static void printShortest(int gen, double dist, Population pop) {
		System.out.println(new StringBuilder("Generation:\t").append(gen));
		System.out.printf("Distance:\t%.3f", dist);
		System.out.println(new StringBuilder("\nSequence:\t").append(
				pop.getFittest()).append("\n"));
	}
}
